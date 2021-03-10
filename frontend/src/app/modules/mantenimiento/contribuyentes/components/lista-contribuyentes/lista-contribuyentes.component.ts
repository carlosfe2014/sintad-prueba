import {Component, OnDestroy, OnInit} from '@angular/core';
import {ContribuyentesService} from "../../services/contribuyentes.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {Subscription} from "rxjs";
import {TipoContribuyente} from "../../models/tipo-contribuyente.model";
import {FormularioContribuyentesComponent} from "../formulario-contribuyentes/formulario-contribuyentes.component";
import Swal from "sweetalert2";

@Component({
  selector: 'app-lista-contribuyentes',
  templateUrl: './lista-contribuyentes.component.html',
  styleUrls: ['./lista-contribuyentes.component.scss']
})
export class ListaContribuyentesComponent implements OnInit, OnDestroy {

  contribuyentes: TipoContribuyente[];
  modalRef: BsModalRef;
  contribuyenteSubscription: Subscription;

  constructor(
    private tipoContribuyentesService: ContribuyentesService,
    private modalService: BsModalService,
  ) { }

  ngOnInit(): void {
    this.tipoContribuyentesService.getAll().subscribe((tipoContribuyentes: TipoContribuyente[]) => {
      this.contribuyentes = tipoContribuyentes;
    });
    this.contribuyenteSubscription = this.tipoContribuyentesService.getEmitter().subscribe((tipoContribuyente: TipoContribuyente) => {
      const indice = this.contribuyentes.findIndex(elemento => {
        return elemento.id === tipoContribuyente.id;
      });
      if (indice !== -1) {
        this.contribuyentes[indice] = tipoContribuyente;
      } else {
        this.contribuyentes.unshift(tipoContribuyente);
      }
    });
  }

  openModal(tipoContribuyente: TipoContribuyente = new TipoContribuyente()) {

    const initialState = {
      titleModal: (!tipoContribuyente.id) ? 'Agregar nuevo contribuyente' : `Editar contribuyente con ID#: ${tipoContribuyente.id}`,
      tipoContribuyente
    };
    this.modalRef = this.modalService.show(FormularioContribuyentesComponent, { initialState, class: 'modal-dialog-centered' });

  }
  deleteElemento(contribuyente: TipoContribuyente) {
    Swal.fire({
      title: `¿Desea eliminar el tipo de contribuyente '${contribuyente.nombre}'?`,
      text: 'Esta acción no puede ser revertida.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.value) {
        this.tipoContribuyentesService.delete(contribuyente).subscribe(ok => {
          Swal.fire(
            '¡Listo!',
            `El tipo de contribuyente fué eliminado correctamente.`,
            'success'
          );
          const indice = this.contribuyentes.findIndex(elemento => {
            return elemento.id === contribuyente.id;
          });
          if (indice !== -1) {
            this.contribuyentes.splice(indice, 1);
          }
        }, error => {
          Swal.fire(
            '¡Error!',
            `El tipo de contribuyente '${contribuyente.nombre}' no pudo ser eliminado.`,
            'error'
          );
        });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.contribuyenteSubscription) {
      this.contribuyenteSubscription.unsubscribe();
    }
  }

}
