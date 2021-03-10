import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthRequest} from "../models/auth-request.model";
import {Observable} from "rxjs";
import {AuthResponse} from "../models/auth.response.model";
import {tap} from "rxjs/operators";
import {environment} from "../../environments/environment";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  headers: HttpHeaders;
  jwtHelper: JwtHelperService;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    this.jwtHelper = new JwtHelperService();
  }

  login(authRequest: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${environment.API_URL}/auth/login`, authRequest, { headers: this.headers })
      .pipe(tap((authResponse: AuthResponse) => {
        if(authResponse.username && authResponse.token){
          this.setUserData(authResponse);
        }
      }));
  }

  getUserFromStorage(): AuthResponse {
    return JSON.parse(localStorage.getItem('user'));
  }

  getToken(): string {
    const user = this.getUserFromStorage();
    return (!!user && !!user.token) ? user.token : null;
  }
  getUser(): string {
    const user = this.getUserFromStorage();
    return (!!user && !!user.username) ? user.username : null;
  }

  setUserData(authResponse: AuthResponse): void {
    localStorage.setItem('user', JSON.stringify(authResponse));
  }

  get isAuthenticated(): boolean {
    const token = this.getToken();
    try {
      return token ? !this.jwtHelper.isTokenExpired(token) : false;
    } catch (e) {
      return false;
    }
  }

  logout(): void {
    localStorage.removeItem('user');
  }

}
