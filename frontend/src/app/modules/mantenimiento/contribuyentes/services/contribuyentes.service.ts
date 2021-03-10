import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {environment} from "../../../../../environments/environment";
import {pluck, tap} from "rxjs/operators";
import {TipoContribuyente} from "../models/tipo-contribuyente.model";
import {SET_HEADERS} from "../../../../utils/general.utils";

@Injectable({
  providedIn: 'root'
})
export class ContribuyentesService {

  private emitter: Subject<TipoContribuyente>;

  constructor(
      private http: HttpClient
  ) {
    this.emitter = new Subject();
  }

  getAll(): Observable<TipoContribuyente[]>{

      const headers = SET_HEADERS(true, false, false, true, '');
      return this.http.get<TipoContribuyente[]>(`${environment.API_URL}/tipocontribuyente`, { headers })
          .pipe(
              pluck('content')
          );
  }

  getEmitter(): Observable<TipoContribuyente> {
    return this.emitter.asObservable();
  }

  save(tipoContribuyenteSave: TipoContribuyente): Observable<TipoContribuyente> {
    const headers = SET_HEADERS(true, true, true, true, 'tipo de contribuyente');
    return this.http.post(`${environment.API_URL}/tipocontribuyente`, tipoContribuyenteSave, { headers }).pipe(tap((tipoContribuyente: TipoContribuyente) => {
      this.emitter.next(tipoContribuyente);
    }));
  }

  update(tipoContribuyenteUpdate: TipoContribuyente): Observable<TipoContribuyente> {
    let id = tipoContribuyenteUpdate.id;
    delete tipoContribuyenteUpdate.id;
    delete tipoContribuyenteUpdate.estado;
    const headers = SET_HEADERS(true, true, true, true, 'tipo de contribuyente');
    return this.http.put(`${environment.API_URL}/tipocontribuyente/${id}`, tipoContribuyenteUpdate, { headers }).pipe(tap((tipoContribuyente: TipoContribuyente) => {
      this.emitter.next(tipoContribuyente);
    }));
  }

  delete(tipoContribuyente: TipoContribuyente): Observable<HttpResponse<any>> {
    const headers = SET_HEADERS(true, true, false, true, '');
    return this.http.delete(`${environment.API_URL}/tipocontribuyente/${tipoContribuyente.id}`, { headers, observe: 'response' }).pipe(tap((response: HttpResponse<any>) => {
      if(response.ok && response.status == 204){
        tipoContribuyente.estado = false;
        this.emitter.next(tipoContribuyente);
      }
    }));
  }
}
