import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from "rxjs/operators";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(
    private authenticationService: AuthService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const newRequest = this.getRequest(request);
    return next
      .handle(newRequest)
      .pipe(catchError((error: HttpErrorResponse) => {
        return throwError(error);
      }));
  }

  getRequest(request: HttpRequest<any>): HttpRequest<any> {
    let newHeaders = request.headers;
    if (request.headers.has('attachToken')) {
      newHeaders = newHeaders.append('Authorization', 'Bearer ' + this.authenticationService.getToken());
      newHeaders = newHeaders.delete('attachToken');
    }
    const tempBody = request.body;
    const modifiedReq = request.clone({
      headers: newHeaders,
      body: tempBody
    });
    return modifiedReq;
  }
}
