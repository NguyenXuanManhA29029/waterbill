import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BacTienHoNgheoService } from '../service/bac-tien-ho-ngheo.service';

import { BacTienHoNgheoComponent } from './bac-tien-ho-ngheo.component';

describe('Component Tests', () => {
  describe('BacTienHoNgheo Management Component', () => {
    let comp: BacTienHoNgheoComponent;
    let fixture: ComponentFixture<BacTienHoNgheoComponent>;
    let service: BacTienHoNgheoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BacTienHoNgheoComponent],
      })
        .overrideTemplate(BacTienHoNgheoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BacTienHoNgheoComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(BacTienHoNgheoService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bacTienHoNgheos?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
