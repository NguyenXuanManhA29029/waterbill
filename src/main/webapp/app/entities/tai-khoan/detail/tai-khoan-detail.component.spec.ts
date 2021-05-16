import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaiKhoanDetailComponent } from './tai-khoan-detail.component';

describe('Component Tests', () => {
  describe('TaiKhoan Management Detail Component', () => {
    let comp: TaiKhoanDetailComponent;
    let fixture: ComponentFixture<TaiKhoanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TaiKhoanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ taiKhoan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TaiKhoanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaiKhoanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load taiKhoan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.taiKhoan).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
