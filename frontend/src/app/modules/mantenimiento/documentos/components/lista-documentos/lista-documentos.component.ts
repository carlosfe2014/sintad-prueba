import { Component, OnInit } from '@angular/core';
import {FormularioDocumentosComponent} from "../formulario-documentos/formulario-documentos.component";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {Subscription} from "rxjs";
import {DocumentosService} from "../../services/documentos.service";
import {TipoDocumento} from "../../models/tipo-documento.model";
import Swal from "sweetalert2";

@Component({
  selector: 'app-lista-documentos',
  templateUrl: './lista-documentos.component.html',
  styleUrls: ['./lista-documentos.component.scss']
})
export class ListaDocumentosComponent implements OnInit {
  tiposDocumentos: TipoDocumento[];
  modalRef: BsModalRef;
  tipoDocumentoSubscription: Subscription;


  constructor(
    private tipoDocumentosService: DocumentosService,
    private modalService: BsModalService,
  ) { }

  ngOnInit(): void {
    this.tipoDocumentosService.getAll().subscribe((tiposDocumentos: TipoDocumento[]) => {
      this.tiposDocumentos = tiposDocumentos;
    });
    this.tipoDocumentoSubscription = this.tipoDocumentosService.getEmitter().subscribe((tipoDocumento: TipoDocumento) => {
      const indice = this.tiposDocumentos.findIndex(elemento => {
        return elemento.id === tipoDocumento.id;
      });
      if (indice !== -1) {
        this.tiposDocumentos[indice] = tipoDocumento;
      } else {
        this.tiposDocumentos.unshift(tipoDocumento);
      }
    });
  }
  openModal(tipoDocumento: TipoDocumento = new TipoDocumento()) {
    const initialState = {
      titleModal: (!tipoDocumento.id) ? 'Agregar nuevo tipo de documento' : `Editar tipo de documento con ID#: ${tipoDocumento.id}`,
      tipoDocumento
    };
    this.modalRef = this.modalService.show(FormularioDocumentosComponent, { initialState, class: 'modal-dialog-centered' });
  }
  deleteElemento(tipoDocumento: TipoDocumento) {
    Swal.fire({
      title: `¿Desea eliminar el tipo de documento '${tipoDocumento.nombre}'?`,
      text: 'Esta acción no puede ser revertida.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.value) {
        this.tipoDocumentosService.delete(tipoDocumento).subscribe(ok => {
          Swal.fire(
            '¡Listo!',
            `El tipo de documento fué eliminado correctamente.`,
            'success'
          );
          const indice = this.tiposDocumentos.findIndex(elemento => {
            return elemento.id === tipoDocumento.id;
          });
          if (indice !== -1) {
            this.tiposDocumentos.splice(indice, 1);
          }
        }, error => {
          Swal.fire(
            '¡Error!',
            `El tipo de documento '${tipoDocumento.nombre}' no pudo ser eliminado.`,
            'error'
          );
        });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.tipoDocumentoSubscription) {
      this.tipoDocumentoSubscription.unsubscribe();
    }
  }

}
