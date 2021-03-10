import { Component, OnInit } from '@angular/core';
import {Entidad} from "../../models/entidad.model";
import {CHECK_INPUT} from "../../../../../utils/general.utils";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef} from "ngx-bootstrap/modal";
import {DocumentosService} from "../../../documentos/services/documentos.service";
import {ContribuyentesService} from "../../../contribuyentes/services/contribuyentes.service";
import {EntidadesService} from "../../services/entidades.service";
import {TipoDocumento} from "../../../documentos/models/tipo-documento.model";
import {TipoContribuyente} from "../../../contribuyentes/models/tipo-contribuyente.model";

@Component({
  selector: 'app-formulario-entidades',
  templateUrl: './formulario-entidades.component.html',
  styleUrls: ['./formulario-entidades.component.scss']
})
export class FormularioEntidadesComponent implements OnInit {


  formulario: FormGroup;
  isSubmited: boolean;
  entidad: Entidad;
  tipoDocumentos: TipoDocumento[];
  tipoContribuyentes: TipoContribuyente[];
  titleModal: string;

  constructor(
      public bsModalRef: BsModalRef,
      private fb: FormBuilder,
      private tipoDocumentoService: DocumentosService,
      private tipoContribuyenteService: ContribuyentesService,
      private entidadService: EntidadesService
  ) {
      this.isSubmited = false;
      this.initForm();
  }



    checkInput(input: string, type: string, errorType: string = ''): boolean {
        return CHECK_INPUT(this.formulario, input , type, errorType, this.isSubmited);
    }
    initForm(): void {
        this.formulario = this.fb.group({
            numeroDocumento: ['', [Validators.required, Validators.maxLength(25)]],
            razonSocial: ['', [Validators.required, Validators.maxLength(100)]],
            nombreComercial: ['', [Validators.maxLength(100)]],
            direccion: [''],
            telefono: [''],
            tipoDocumentoId: ['', [Validators.required, Validators.min(1)]],
            tipoContribuyenteId: ['', [Validators.min(1)]],
        });
    }
    ngOnInit(): void {

        this.tipoContribuyenteService.getAll().subscribe((tipoContribuyentes: TipoContribuyente[]) => {
            this.tipoContribuyentes = tipoContribuyentes;
        });

        this.tipoDocumentoService.getAll().subscribe((tipoDocumento: TipoDocumento[]) => {
            this.tipoDocumentos = tipoDocumento;
        });

        this.formulario.get('numeroDocumento').setValue(this.entidad.numeroDocumento);
        this.formulario.get('razonSocial').setValue(this.entidad.razonSocial);
        this.formulario.get('nombreComercial').setValue(this.entidad.nombreComercial);
        this.formulario.get('direccion').setValue(this.entidad.direccion);
        this.formulario.get('telefono').setValue(this.entidad.telefono);


        if(this.entidad.tipoDocumentoId){
            this.formulario.get('tipoDocumentoId').setValue(this.entidad.tipoDocumentoId);
        }

        if(this.entidad.tipoContribuyenteId){
            this.formulario.get('tipoContribuyenteId').setValue(this.entidad.tipoContribuyenteId);
        }
    }
    save(): void {
        this.isSubmited = true;
        if (this.formulario.invalid) {
            return;
        }
        const entidadTemporal: Entidad = {
            ...this.entidad
        }
        entidadTemporal.numeroDocumento = this.formulario.value.numeroDocumento;
        entidadTemporal.razonSocial = this.formulario.value.razonSocial;
        entidadTemporal.nombreComercial = this.formulario.value.nombreComercial;
        entidadTemporal.direccion = this.formulario.value.direccion;
        entidadTemporal.telefono = this.formulario.value.telefono;
        entidadTemporal.tipoDocumentoId = this.formulario.value.tipoDocumentoId;

        if(this.formulario.value.tipoContribuyenteId){
            entidadTemporal.tipoContribuyenteId = this.formulario.value.tipoContribuyenteId;
        } else {
            entidadTemporal.tipoContribuyenteId = null;
        }

        if (!!this.entidad.id) {
            this.entidadService.update(entidadTemporal).subscribe(response => {
                if (response.id) {
                    this.bsModalRef.hide();
                }
            });
        } else {
            this.entidadService.save(entidadTemporal).subscribe(response => {
                if (response.id) {
                    this.bsModalRef.hide();
                }
            });
        }
    }
}
