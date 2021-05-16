import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ThueDetailComponent } from './thue-detail.component';

describe('Component Tests', () => {
  describe('Thue Management Detail Component', () => {
    let comp: ThueDetailComponent;
    let fixture: ComponentFixture<ThueDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ThueDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ thue: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ThueDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ThueDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load thue on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.thue).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
