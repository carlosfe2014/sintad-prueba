import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioContribuyentesComponent } from './formulario-contribuyentes.component';

describe('FormularioContribuyentesComponent', () => {
  let component: FormularioContribuyentesComponent;
  let fixture: ComponentFixture<FormularioContribuyentesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormularioContribuyentesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormularioContribuyentesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
