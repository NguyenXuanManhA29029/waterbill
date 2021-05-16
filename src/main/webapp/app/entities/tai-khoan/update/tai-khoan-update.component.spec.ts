jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TaiKhoanService } from '../service/tai-khoan.service';
import { ITaiKhoan, TaiKhoan } from '../tai-khoan.model';

import { TaiKhoanUpdateComponent } from './tai-khoan-update.component';

describe('Component Tests', () => {
  describe('TaiKhoan Management Update Component', () => {
    let comp: TaiKhoanUpdateComponent;
    let fixture: ComponentFixture<TaiKhoanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let taiKhoanService: TaiKhoanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaiKhoanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(TaiKhoanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TaiKhoanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      taiKhoanService = TestBed.inject(TaiKhoanService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const taiKhoan: ITaiKhoan = { id: 456 };

        activatedRoute.data = of({ taiKhoan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(taiKhoan));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taiKhoan = { id: 123 };
        spyOn(taiKhoanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taiKhoan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taiKhoan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(taiKhoanService.update).toHaveBeenCalledWith(taiKhoan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taiKhoan = new TaiKhoan();
        spyOn(taiKhoanService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taiKhoan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: taiKhoan }));
        saveSubject.complete();

        // THEN
        expect(taiKhoanService.create).toHaveBeenCalledWith(taiKhoan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const taiKhoan = { id: 123 };
        spyOn(taiKhoanService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ taiKhoan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(taiKhoanService.update).toHaveBeenCalledWith(taiKhoan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
