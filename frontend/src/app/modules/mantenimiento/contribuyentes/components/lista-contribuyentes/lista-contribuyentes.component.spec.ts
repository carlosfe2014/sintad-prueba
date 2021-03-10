import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaContribuyentesComponent } from './lista-contribuyentes.component';

describe('ListaContribuyentesComponent', () => {
  let component: ListaContribuyentesComponent;
  let fixture: ComponentFixture<ListaContribuyentesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaContribuyentesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaContribuyentesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
