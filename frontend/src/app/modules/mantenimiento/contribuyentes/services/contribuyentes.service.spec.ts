import { TestBed } from '@angular/core/testing';

import { ContribuyentesService } from './contribuyentes.service';

describe('ContribuyentesService', () => {
  let service: ContribuyentesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContribuyentesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
