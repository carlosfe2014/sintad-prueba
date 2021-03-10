import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MantenimientoRoutingModule } from './mantenimiento-routing.module';
import {TableModule} from "primeng/table";
import {ListaEntidadesComponent} from "./entidades/components/lista-entidades/lista-entidades.component";
import {ButtonModule} from "primeng/button";
import {ToolbarModule} from "primeng/toolbar";
import {PanelModule} from "primeng/panel";
import {TagModule} from "primeng/tag";
import { ListaContribuyentesComponent } from './contribuyentes/components/lista-contribuyentes/lista-contribuyentes.component';
import { ListaDocumentosComponent } from './documentos/components/lista-documentos/lista-documentos.component';
import {HttpClientModule} from "@angular/common/http";
import {ModalModule} from "ngx-bootstrap/modal";
import { FormularioEntidadesComponent } from './entidades/components/formulario-entidades/formulario-entidades.component';
import {ReactiveFormsModule} from "@angular/forms";
import { FormularioContribuyentesComponent } from './contribuyentes/components/formulario-contribuyentes/formulario-contribuyentes.component';
import { FormularioDocumentosComponent } from './documentos/components/formulario-documentos/formulario-documentos.component';

@NgModule({
  declarations: [ListaEntidadesComponent, ListaContribuyentesComponent, ListaDocumentosComponent, FormularioEntidadesComponent, FormularioContribuyentesComponent, FormularioDocumentosComponent],
  imports: [
    CommonModule,
    MantenimientoRoutingModule,
    TableModule,
    ButtonModule,
    ToolbarModule,
    PanelModule,
    TagModule,
    ModalModule.forRoot(),
    HttpClientModule,
    ReactiveFormsModule
  ]
})
export class MantenimientoModule { }
