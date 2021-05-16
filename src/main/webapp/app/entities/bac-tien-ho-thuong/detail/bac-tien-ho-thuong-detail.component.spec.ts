import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BacTienHoThuongDetailComponent } from './bac-tien-ho-thuong-detail.component';

describe('Component Tests', () => {
  describe('BacTienHoThuong Management Detail Component', () => {
    let comp: BacTienHoThuongDetailComponent;
    let fixture: ComponentFixture<BacTienHoThuongDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BacTienHoThuongDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ bacTienHoThuong: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BacTienHoThuongDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BacTienHoThuongDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bacTienHoThuong on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bacTienHoThuong).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
