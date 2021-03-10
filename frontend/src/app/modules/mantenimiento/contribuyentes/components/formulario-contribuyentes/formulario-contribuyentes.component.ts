import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TipoContribuyente} from "../../models/tipo-contribuyente.model";
import {BsModalRef} from "ngx-bootstrap/modal";
import {ContribuyentesService} from "../../services/contribuyentes.service";
import {CHECK_INPUT} from "../../../../../utils/general.utils";

@Component({
  selector: 'app-formulario-contribuyentes',
  templateUrl: './formulario-contribuyentes.component.html',
  styleUrls: ['./formulario-contribuyentes.component.scss']
})
export class FormularioContribuyentesComponent implements OnInit {


  formulario: FormGroup;
  isSubmited: boolean;
  tipoContribuyente: TipoContribuyente;
  titleModal: string;

  constructor(
    public bsModalRef: BsModalRef,
    private fb: FormBuilder,
    private tipoContribuyenteService: ContribuyentesService
  ) {
    this.isSubmited = false;
    this.initForm();
  }
  checkInput(input: string, type: string, errorType: string = ''): boolean {
    return CHECK_INPUT(this.formulario, input , type, errorType, this.isSubmited);
  }
  initForm(): void {
    this.formulario = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(50)]]
    });
  }

  ngOnInit(): void {
    this.formulario.get('nombre').setValue(this.tipoContribuyente.nombre);
  }

  save(): void {
    this.isSubmited = true;
    if (this.formulario.invalid) {
      return;
    }
    const tipoContribuyenteTemporal: TipoContribuyente = {
      ...this.tipoContribuyente
    }
    tipoContribuyenteTemporal.nombre = this.formulario.value.nombre;
    if (!!this.tipoContribuyente.id) {
      this.tipoContribuyenteService.update(tipoContribuyenteTemporal).subscribe(response => {
        if (response.id) {
          this.bsModalRef.hide();
        }
      });
    } else {
      this.tipoContribuyenteService.save(tipoContribuyenteTemporal).subscribe(response => {
        if (response.id) {
          this.bsModalRef.hide();
        }
      });
    }
  }
}
