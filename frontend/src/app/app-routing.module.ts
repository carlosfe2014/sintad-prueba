import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LayoutAdminComponent} from "./layouts/layout-admin/layout-admin.component";
import {LayoutLoginComponent} from "./layouts/layout-login/layout-login.component";
import {AuthGuard} from "./guards/auth.guard";


const routes: Routes = [

  { path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  { path: 'login',
    component: LayoutLoginComponent,
  },
  {
    path: 'admin',
    component: LayoutAdminComponent,
    children: [
      {
        path: 'mantenimiento',
        loadChildren: () => import('./modules/mantenimiento/mantenimiento.module').then(m => m.MantenimientoModule)
      },
      { path: '**',
          redirectTo: '/admin/mantenimiento/entidades',
          pathMatch: 'full'
      }

    ],
    canActivate:[AuthGuard]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
