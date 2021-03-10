import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import Swal from "sweetalert2";
import {catchError, delay, map} from "rxjs/operators";

@Injectable()
export class AlertInterceptor implements HttpInterceptor {
  isAlert: boolean;
  delayRequest: boolean;
  showAlertError: boolean;
  constructor() {
    this.isAlert = false;
    this.delayRequest = false;
    this.showAlertError = false;
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const newRequest = this.processRequest(request);
    let delayTime = 0;
    if (this.delayRequest) {
      delayTime = 500;
    }
    return next.handle(newRequest).pipe(delay(delayTime), map(response => {
      if (this.isAlert) Swal.close();
      return response;
    })).pipe(catchError((error: HttpErrorResponse) => {
      this.processError(error);
      return throwError(error);
    }));
  }

  processRequest(request: HttpRequest<any>): HttpRequest<any> {
    let newHeaders = request.headers;

    let elemento = 'recurso';
    if (request.headers.has('setElement')) {
      elemento = request.headers.get('setElement');
      newHeaders = newHeaders.delete('setElement');
    }

    if (request.headers.has('showAlert')) {
      this.showAlert(request, elemento);
      newHeaders = newHeaders.delete('showAlert');
    }

    if (request.headers.has('delayRequest')) {
      this.delayRequest = true;
      newHeaders = newHeaders.delete('delayRequest');
    } else {
      this.delayRequest = false;
    }
    if (request.headers.has('showAlertError')) {
      this.showAlertError = true;
      newHeaders = newHeaders.delete('showAlertError');
    } else {
      this.showAlertError = false;
    }
    const tempBody = request.body;
    const modifiedReq = request.clone({
      headers: newHeaders,
      body: tempBody
    });
    return modifiedReq;
  }

  showAlert(request: HttpRequest<unknown>, elemento: string): void {
    console.log("alert");
    let titulo: string;
    switch (request.method) {
      case 'POST':
        this.isAlert = true;
        titulo = `Creando ${elemento}...`;
        break;
      case 'PUT':
        this.isAlert = true;
        titulo = `¡Actualizando ${elemento}!`;
        break;
      default:
    }
    if (this.isAlert) {
      Swal.fire({
        title: titulo,
        text: 'Por favor, espere un momento',
        icon: 'info',
      });
      Swal.showLoading();
    }
  }

  processError(error: HttpErrorResponse) {
    console.log(error);
    if (this.showAlertError && !!error.error) {
      let message = error.error.error;
      console.log(message);
      if (!!error.error.fieldErrors) {
        message = message + `<br><ul style="text-align: left; font-size: 15px; margin-top: 10px;">`;
        error.error.fieldErrors.forEach((errorApi: any) => {
          message = message + `<li>${errorApi.message}</li>`;
        });
        message = message + `</ul>`;
      }
      Swal.fire({
        title: '¡Error!',
        icon: 'error',
        html: message
      });
    } else {
      Swal.close();
    }
  }


}
