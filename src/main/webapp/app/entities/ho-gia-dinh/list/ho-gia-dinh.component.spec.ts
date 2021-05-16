import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HoGiaDinhService } from '../service/ho-gia-dinh.service';

import { HoGiaDinhComponent } from './ho-gia-dinh.component';

describe('Component Tests', () => {
  describe('HoGiaDinh Management Component', () => {
    let comp: HoGiaDinhComponent;
    let fixture: ComponentFixture<HoGiaDinhComponent>;
    let service: HoGiaDinhService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HoGiaDinhComponent],
      })
        .overrideTemplate(HoGiaDinhComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HoGiaDinhComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(HoGiaDinhService);

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
      expect(comp.hoGiaDinhs?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
