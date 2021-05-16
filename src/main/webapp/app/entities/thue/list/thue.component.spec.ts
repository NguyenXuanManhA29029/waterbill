import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ThueService } from '../service/thue.service';

import { ThueComponent } from './thue.component';

describe('Component Tests', () => {
  describe('Thue Management Component', () => {
    let comp: ThueComponent;
    let fixture: ComponentFixture<ThueComponent>;
    let service: ThueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ThueComponent],
      })
        .overrideTemplate(ThueComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThueComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ThueService);

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
      expect(comp.thues?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
