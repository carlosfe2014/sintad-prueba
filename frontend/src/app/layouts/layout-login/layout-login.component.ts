import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthRequest} from "../../models/auth-request.model";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {CHECK_INPUT} from "../../utils/general.utils";

@Component({
  selector: 'app-layout-login',
  templateUrl: './layout-login.component.html',
  styleUrls: ['./layout-login.component.scss']
})
export class LayoutLoginComponent implements OnInit {

  formulario: FormGroup;
  isSubmited: boolean;
  errorMessage: string;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.errorMessage = null;
    this.isSubmited = false;
    this.initForm();
  }


  ngOnInit(): void {
  }

  checkInput(input: string, type: string, errorType: string = ''): boolean {
      return CHECK_INPUT(this.formulario, input , type, errorType, this.isSubmited);
  }

  initForm(): void{
    this.formulario = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]]
    });
  }


  save(): void{
    this.errorMessage = null;
    this.isSubmited = true;
    if (this.formulario.invalid) {
      return;
    }
    const authRequest: AuthRequest = {
      username: this.formulario.value.username,
      password: this.formulario.value.password
    }
    this.authService.login(authRequest).subscribe((authResponse) => {
      if(authResponse.token && this.authService.getToken() === authResponse.token){
        this.router.navigate(['/admin']);
      }
    }, error => {
      if(error.error?.error) {
        this.errorMessage = error.error?.error;
      }
    });
  }
}
