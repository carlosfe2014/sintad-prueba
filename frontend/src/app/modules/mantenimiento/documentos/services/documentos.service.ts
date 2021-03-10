import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../../../../environments/environment";
import {pluck, tap} from "rxjs/operators";
import {TipoDocumento} from "../models/tipo-documento.model";
import {SET_HEADERS} from "../../../../utils/general.utils";

@Injectable({
  providedIn: 'root'
})
export class DocumentosService {

    private emitter: Subject<TipoDocumento>;
    constructor(
        private http: HttpClient
    ) {
      this.emitter = new Subject();
    }

    getEmitter(): Observable<TipoDocumento> {
      return this.emitter.asObservable();
    }

    getAll(): Observable<TipoDocumento[]>{
      const headers = SET_HEADERS(true, false, false, true, '');
        return this.http.get<TipoDocumento[]>(`${environment.API_URL}/tipodocumento`, { headers })
            .pipe(
                pluck('content')
            );
    }

  save(tipoDocumentoSave: TipoDocumento): Observable<TipoDocumento> {
    const headers = SET_HEADERS(true, true, true, true, 'tipo de documento');
    return this.http.post(`${environment.API_URL}/tipodocumento`, tipoDocumentoSave, { headers }).pipe(tap((tipoDocumento: TipoDocumento) => {
      this.emitter.next(tipoDocumento);
    }));
  }

  update(tipoDocumentoUpdate: TipoDocumento): Observable<TipoDocumento> {
    let id = tipoDocumentoUpdate.id;
    delete tipoDocumentoUpdate.id;
    delete tipoDocumentoUpdate.estado;
    const headers = SET_HEADERS(true, true, true, true, 'tipo de documento');
    return this.http.put(`${environment.API_URL}/tipodocumento/${id}`, tipoDocumentoUpdate, { headers }).pipe(tap((tipoDocumento: TipoDocumento) => {
      this.emitter.next(tipoDocumento);
    }));
  }

  delete(tipoDocumento: TipoDocumento): Observable<HttpResponse<any>> {
    const headers = SET_HEADERS(true, true, false, true, '');
    return this.http.delete(`${environment.API_URL}/tipodocumento/${tipoDocumento.id}`, { headers, observe: 'response' }).pipe(tap((response: HttpResponse<any>) => {
      if(response.ok && response.status == 204){
        tipoDocumento.estado = false;
        this.emitter.next(tipoDocumento);
      }
    }));
  }
}
