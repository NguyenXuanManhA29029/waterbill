import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';

import { BacTienHoThuongComponent } from './bac-tien-ho-thuong.component';

describe('Component Tests', () => {
  describe('BacTienHoThuong Management Component', () => {
    let comp: BacTienHoThuongComponent;
    let fixture: ComponentFixture<BacTienHoThuongComponent>;
    let service: BacTienHoThuongService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BacTienHoThuongComponent],
      })
        .overrideTemplate(BacTienHoThuongComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BacTienHoThuongComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(BacTienHoThuongService);

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
      expect(comp.bacTienHoThuongs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
