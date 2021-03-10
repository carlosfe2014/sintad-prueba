import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioEntidadesComponent } from './formulario-entidades.component';

describe('FormularioEntidadesComponent', () => {
  let component: FormularioEntidadesComponent;
  let fixture: ComponentFixture<FormularioEntidadesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormularioEntidadesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormularioEntidadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
