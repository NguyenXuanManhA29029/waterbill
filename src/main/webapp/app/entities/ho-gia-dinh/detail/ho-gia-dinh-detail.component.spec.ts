import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HoGiaDinhDetailComponent } from './ho-gia-dinh-detail.component';

describe('Component Tests', () => {
  describe('HoGiaDinh Management Detail Component', () => {
    let comp: HoGiaDinhDetailComponent;
    let fixture: ComponentFixture<HoGiaDinhDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HoGiaDinhDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ hoGiaDinh: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HoGiaDinhDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HoGiaDinhDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hoGiaDinh on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hoGiaDinh).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
