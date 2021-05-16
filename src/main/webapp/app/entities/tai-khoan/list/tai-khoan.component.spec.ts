import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TaiKhoanService } from '../service/tai-khoan.service';

import { TaiKhoanComponent } from './tai-khoan.component';

describe('Component Tests', () => {
  describe('TaiKhoan Management Component', () => {
    let comp: TaiKhoanComponent;
    let fixture: ComponentFixture<TaiKhoanComponent>;
    let service: TaiKhoanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaiKhoanComponent],
      })
        .overrideTemplate(TaiKhoanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaiKhoanComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaiKhoanService);

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
      expect(comp.taiKhoans?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
