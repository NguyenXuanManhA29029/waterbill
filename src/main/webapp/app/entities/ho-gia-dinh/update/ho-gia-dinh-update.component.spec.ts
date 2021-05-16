jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HoGiaDinhService } from '../service/ho-gia-dinh.service';
import { IHoGiaDinh, HoGiaDinh } from '../ho-gia-dinh.model';
import { ITaiKhoan } from 'app/entities/tai-khoan/tai-khoan.model';
import { TaiKhoanService } from 'app/entities/tai-khoan/service/tai-khoan.service';

import { HoGiaDinhUpdateComponent } from './ho-gia-dinh-update.component';

describe('Component Tests', () => {
  describe('HoGiaDinh Management Update Component', () => {
    let comp: HoGiaDinhUpdateComponent;
    let fixture: ComponentFixture<HoGiaDinhUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hoGiaDinhService: HoGiaDinhService;
    let taiKhoanService: TaiKhoanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HoGiaDinhUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HoGiaDinhUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HoGiaDinhUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hoGiaDinhService = TestBed.inject(HoGiaDinhService);
      taiKhoanService = TestBed.inject(TaiKhoanService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call taikhoan query and add missing value', () => {
        const hoGiaDinh: IHoGiaDinh = { id: 456 };
        const taikhoan: ITaiKhoan = { id: 36677 };
        hoGiaDinh.taikhoan = taikhoan;

        const taikhoanCollection: ITaiKhoan[] = [{ id: 33485 }];
        spyOn(taiKhoanService, 'query').and.returnValue(of(new HttpResponse({ body: taikhoanCollection })));
        const expectedCollection: ITaiKhoan[] = [taikhoan, ...taikhoanCollection];
        spyOn(taiKhoanService, 'addTaiKhoanToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hoGiaDinh });
        comp.ngOnInit();

        expect(taiKhoanService.query).toHaveBeenCalled();
        expect(taiKhoanService.addTaiKhoanToCollectionIfMissing).toHaveBeenCalledWith(taikhoanCollection, taikhoan);
        expect(comp.taikhoansCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const hoGiaDinh: IHoGiaDinh = { id: 456 };
        const taikhoan: ITaiKhoan = { id: 14377 };
        hoGiaDinh.taikhoan = taikhoan;

        activatedRoute.data = of({ hoGiaDinh });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hoGiaDinh));
        expect(comp.taikhoansCollection).toContain(taikhoan);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hoGiaDinh = { id: 123 };
        spyOn(hoGiaDinhService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoGiaDinh });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hoGiaDinh }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hoGiaDinhService.update).toHaveBeenCalledWith(hoGiaDinh);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hoGiaDinh = new HoGiaDinh();
        spyOn(hoGiaDinhService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoGiaDinh });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hoGiaDinh }));
        saveSubject.complete();

        // THEN
        expect(hoGiaDinhService.create).toHaveBeenCalledWith(hoGiaDinh);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hoGiaDinh = { id: 123 };
        spyOn(hoGiaDinhService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoGiaDinh });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hoGiaDinhService.update).toHaveBeenCalledWith(hoGiaDinh);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTaiKhoanById', () => {
        it('Should return tracked TaiKhoan primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTaiKhoanById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
