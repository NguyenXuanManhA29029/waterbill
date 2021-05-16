import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BacTienHoNgheoDetailComponent } from './bac-tien-ho-ngheo-detail.component';

describe('Component Tests', () => {
  describe('BacTienHoNgheo Management Detail Component', () => {
    let comp: BacTienHoNgheoDetailComponent;
    let fixture: ComponentFixture<BacTienHoNgheoDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BacTienHoNgheoDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ bacTienHoNgheo: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BacTienHoNgheoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BacTienHoNgheoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bacTienHoNgheo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bacTienHoNgheo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
