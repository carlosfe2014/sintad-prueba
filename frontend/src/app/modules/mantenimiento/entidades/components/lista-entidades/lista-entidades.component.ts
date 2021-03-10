import {Component, OnDestroy, OnInit} from '@angular/core';
import {Entidad} from "../../models/entidad.model";
import {EntidadesService} from "../../services/entidades.service";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {FormularioEntidadesComponent} from "../formulario-entidades/formulario-entidades.component";
import {Subscription} from "rxjs";
import Swal from "sweetalert2";

@Component({
  selector: 'app-lista-entidades',
  templateUrl: './lista-entidades.component.html',
  styleUrls: ['./lista-entidades.component.scss']
})
export class ListaEntidadesComponent implements OnInit, OnDestroy {
  entidades: Entidad[];
  modalRef: BsModalRef;
  entidadSubscription: Subscription;

  constructor(
      private entidadesService: EntidadesService,
      private modalService: BsModalService,
  ) { }

  ngOnInit(): void {
    this.entidadesService.getAll().subscribe((entidades: Entidad[]) => {
      this.entidades = entidades;
    });

    this.entidadSubscription = this.entidadesService.getEmitter().subscribe((entidad: Entidad) => {
        const indice = this.entidades.findIndex(elemento => {
            return elemento.id === entidad.id;
        });
        if (indice !== -1) {
            this.entidades[indice] = entidad;
        } else {
            this.entidades.unshift(entidad);
        }
    });
  }

  openModal(entidad: Entidad = new Entidad()) {
      const initialState = {
          titleModal: (!entidad.id) ? 'Agregar nueva entidad' : `Editar Entidad con ID#: ${entidad.id}`,
          entidad
      };
      this.modalRef = this.modalService.show(FormularioEntidadesComponent, { initialState, class: 'modal-dialog-centered' });

  }
  deleteElemento(entidad: Entidad) {
    Swal.fire({
      title: `¿Desea eliminar la entidad con ID# '${entidad.id}'?`,
      text: 'Esta acción no puede ser revertida.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.value) {
        this.entidadesService.delete(entidad).subscribe(ok => {
          Swal.fire(
            '¡Listo!',
            `La entidad fué eliminada correctamente.`,
            'success'
          );
          const indice = this.entidades.findIndex(elemento => {
            return elemento.id === entidad.id;
          });
          if (indice !== -1) {
            this.entidades.splice(indice, 1);
          }
        }, error => {
          Swal.fire(
            '¡Error!',
            `La entidad con ID# '${entidad.id}' no pudo ser eliminada.`,
            'error'
          );
        });
      }
    });





  }

  ngOnDestroy(): void {
    if (this.entidadSubscription) {
      this.entidadSubscription.unsubscribe();
    }
  }
}
