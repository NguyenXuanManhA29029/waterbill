jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BacTienHoNgheoService } from '../service/bac-tien-ho-ngheo.service';
import { IBacTienHoNgheo, BacTienHoNgheo } from '../bac-tien-ho-ngheo.model';

import { BacTienHoNgheoUpdateComponent } from './bac-tien-ho-ngheo-update.component';

describe('Component Tests', () => {
  describe('BacTienHoNgheo Management Update Component', () => {
    let comp: BacTienHoNgheoUpdateComponent;
    let fixture: ComponentFixture<BacTienHoNgheoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let bacTienHoNgheoService: BacTienHoNgheoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BacTienHoNgheoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BacTienHoNgheoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BacTienHoNgheoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      bacTienHoNgheoService = TestBed.inject(BacTienHoNgheoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const bacTienHoNgheo: IBacTienHoNgheo = { id: 456 };

        activatedRoute.data = of({ bacTienHoNgheo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(bacTienHoNgheo));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bacTienHoNgheo = { id: 123 };
        spyOn(bacTienHoNgheoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bacTienHoNgheo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bacTienHoNgheo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(bacTienHoNgheoService.update).toHaveBeenCalledWith(bacTienHoNgheo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bacTienHoNgheo = new BacTienHoNgheo();
        spyOn(bacTienHoNgheoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bacTienHoNgheo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bacTienHoNgheo }));
        saveSubject.complete();

        // THEN
        expect(bacTienHoNgheoService.create).toHaveBeenCalledWith(bacTienHoNgheo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bacTienHoNgheo = { id: 123 };
        spyOn(bacTienHoNgheoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bacTienHoNgheo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(bacTienHoNgheoService.update).toHaveBeenCalledWith(bacTienHoNgheo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
