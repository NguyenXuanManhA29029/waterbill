jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ThueService } from '../service/thue.service';
import { IThue, Thue } from '../thue.model';

import { ThueUpdateComponent } from './thue-update.component';

describe('Component Tests', () => {
  describe('Thue Management Update Component', () => {
    let comp: ThueUpdateComponent;
    let fixture: ComponentFixture<ThueUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let thueService: ThueService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ThueUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ThueUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ThueUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      thueService = TestBed.inject(ThueService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const thue: IThue = { id: 456 };

        activatedRoute.data = of({ thue });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(thue));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const thue = { id: 123 };
        spyOn(thueService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ thue });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: thue }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(thueService.update).toHaveBeenCalledWith(thue);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const thue = new Thue();
        spyOn(thueService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ thue });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: thue }));
        saveSubject.complete();

        // THEN
        expect(thueService.create).toHaveBeenCalledWith(thue);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const thue = { id: 123 };
        spyOn(thueService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ thue });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(thueService.update).toHaveBeenCalledWith(thue);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
