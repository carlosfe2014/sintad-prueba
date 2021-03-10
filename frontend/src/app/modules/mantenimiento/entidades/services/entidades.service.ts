import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import { Observable, Subject} from "rxjs";
import {environment} from "../../../../../environments/environment";
import {pluck, tap} from "rxjs/operators";
import {Entidad} from "../models/entidad.model";
import {SET_HEADERS} from "../../../../utils/general.utils";

@Injectable({
  providedIn: 'root'
})
export class EntidadesService {

    private emitter: Subject<Entidad>;
    constructor(
      private http: HttpClient
    ) {
        this.emitter = new Subject();
    }

    getEmitter(): Observable<Entidad> {
        return this.emitter.asObservable();
    }

    getAll(): Observable<Entidad[]>{
      const headers = SET_HEADERS(true, false, false, true, '');
      return this.http.get<Entidad[]>(`${environment.API_URL}/entidades`, { headers })
          .pipe(
              pluck('content')
          );
    }

    save(entidadSave: Entidad): Observable<Entidad> {
        const headers = SET_HEADERS(true, true, true, true, 'entidad');
        return this.http.post(`${environment.API_URL}/entidades`, entidadSave, { headers }).pipe(tap((entidad: Entidad) => {
            this.emitter.next(entidad);
        }));
    }

    update(entidadUpdate: Entidad): Observable<Entidad> {
        let id = entidadUpdate.id;
        delete entidadUpdate.id;
        delete entidadUpdate.estado;
        const headers = SET_HEADERS(true, true, true, true, 'entidad');
        return this.http.put(`${environment.API_URL}/entidades/${id}`, entidadUpdate, { headers }).pipe(tap((entidad: Entidad) => {
            this.emitter.next(entidad);
        }));
    }

    delete(entidad: Entidad): Observable<HttpResponse<any>> {
      const headers = SET_HEADERS(true, true, false, true, '');
      return this.http.delete(`${environment.API_URL}/entidades/${entidad.id}`, { headers, observe: 'response' }).pipe(tap((response: HttpResponse<any>) => {
          if(response.ok && response.status == 204){
            entidad.estado = false;
            this.emitter.next(entidad);
          }
      }));
    }
}
