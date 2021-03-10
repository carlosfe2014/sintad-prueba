import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ListaEntidadesComponent} from "./entidades/components/lista-entidades/lista-entidades.component";
import {ListaContribuyentesComponent} from "./contribuyentes/components/lista-contribuyentes/lista-contribuyentes.component";
import {ListaDocumentosComponent} from "./documentos/components/lista-documentos/lista-documentos.component";
import {AuthGuard} from "../../guards/auth.guard";


const routes: Routes = [
  {
    path: 'entidades',
    component: ListaEntidadesComponent,
    canActivate:[AuthGuard]
  },
  {
    path: 'contribuyentes',
    component: ListaContribuyentesComponent,
    canActivate:[AuthGuard]
  },
  {
    path: 'documentos',
    component: ListaDocumentosComponent,
    canActivate:[AuthGuard]
  },


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MantenimientoRoutingModule { }
