import { Component, OnInit } from '@angular/core';
import {CHECK_INPUT} from "../../../../../utils/general.utils";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TipoDocumento} from "../../models/tipo-documento.model";
import {BsModalRef} from "ngx-bootstrap/modal";
import {DocumentosService} from "../../services/documentos.service";

@Component({
  selector: 'app-formulario-documentos',
  templateUrl: './formulario-documentos.component.html',
  styleUrls: ['./formulario-documentos.component.scss']
})
export class FormularioDocumentosComponent implements OnInit {

  formulario: FormGroup;
  isSubmited: boolean;
  tipoDocumento: TipoDocumento;
  titleModal: string;

  constructor(
    public bsModalRef: BsModalRef,
    private fb: FormBuilder,
    private tipoDocumentosService: DocumentosService
  ) {
    this.isSubmited = false;
    this.initForm();
  }

  ngOnInit(): void {
    this.formulario.get('nombre').setValue(this.tipoDocumento.nombre);
    this.formulario.get('codigo').setValue(this.tipoDocumento.codigo);
    this.formulario.get('descripcion').setValue(this.tipoDocumento.descripcion);
  }

  checkInput(input: string, type: string, errorType: string = ''): boolean {
    return CHECK_INPUT(this.formulario, input , type, errorType, this.isSubmited);
  }
  initForm(): void {
    this.formulario = this.fb.group({
      codigo: ['', [Validators.required, Validators.maxLength(20)]],
      nombre: ['', [Validators.required, Validators.maxLength(100)]],
      descripcion: ['', [Validators.maxLength(200)]]
    });
  }

  save(): void {
    this.isSubmited = true;
    if (this.formulario.invalid) {
      return;
    }
    const tipoDocumentoTemporal: TipoDocumento = {
      ...this.tipoDocumento
    }
    tipoDocumentoTemporal.nombre = this.formulario.value.nombre;
    tipoDocumentoTemporal.codigo = this.formulario.value.codigo;
    tipoDocumentoTemporal.descripcion = this.formulario.value.descripcion;
    if (!!this.tipoDocumento.id) {
      this.tipoDocumentosService.update(tipoDocumentoTemporal).subscribe(response => {
        if (response.id) {
          this.bsModalRef.hide();
        }
      });
    } else {
      this.tipoDocumentosService.save(tipoDocumentoTemporal).subscribe(response => {
        if (response.id) {
          this.bsModalRef.hide();
        }
      });
    }
  }
}
