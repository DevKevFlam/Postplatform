import { TestBed } from '@angular/core/testing';

import { RegistationService } from './registation.service';

describe('RegistationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RegistationService = TestBed.get(RegistationService);
    expect(service).toBeTruthy();
  });
});
