jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HoaDonService } from '../service/hoa-don.service';
import { IHoaDon, HoaDon } from '../hoa-don.model';
import { IThue } from 'app/entities/thue/thue.model';
import { ThueService } from 'app/entities/thue/service/thue.service';
import { IBacTienHoNgheo } from 'app/entities/bac-tien-ho-ngheo/bac-tien-ho-ngheo.model';
import { BacTienHoNgheoService } from 'app/entities/bac-tien-ho-ngheo/service/bac-tien-ho-ngheo.service';
import { IBacTienHoThuong } from 'app/entities/bac-tien-ho-thuong/bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from 'app/entities/bac-tien-ho-thuong/service/bac-tien-ho-thuong.service';
import { IHoGiaDinh } from 'app/entities/ho-gia-dinh/ho-gia-dinh.model';
import { HoGiaDinhService } from 'app/entities/ho-gia-dinh/service/ho-gia-dinh.service';

import { HoaDonUpdateComponent } from './hoa-don-update.component';

describe('Component Tests', () => {
  describe('HoaDon Management Update Component', () => {
    let comp: HoaDonUpdateComponent;
    let fixture: ComponentFixture<HoaDonUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hoaDonService: HoaDonService;
    let thueService: ThueService;
    let bacTienHoNgheoService: BacTienHoNgheoService;
    let bacTienHoThuongService: BacTienHoThuongService;
    let hoGiaDinhService: HoGiaDinhService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HoaDonUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HoaDonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HoaDonUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hoaDonService = TestBed.inject(HoaDonService);
      thueService = TestBed.inject(ThueService);
      bacTienHoNgheoService = TestBed.inject(BacTienHoNgheoService);
      bacTienHoThuongService = TestBed.inject(BacTienHoThuongService);
      hoGiaDinhService = TestBed.inject(HoGiaDinhService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call thue query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const thue: IThue = { id: 68179 };
        hoaDon.thue = thue;

        const thueCollection: IThue[] = [{ id: 72352 }];
        spyOn(thueService, 'query').and.returnValue(of(new HttpResponse({ body: thueCollection })));
        const expectedCollection: IThue[] = [thue, ...thueCollection];
        spyOn(thueService, 'addThueToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(thueService.query).toHaveBeenCalled();
        expect(thueService.addThueToCollectionIfMissing).toHaveBeenCalledWith(thueCollection, thue);
        expect(comp.thuesCollection).toEqual(expectedCollection);
      });

      it('Should call bacHoNgheo query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const bacHoNgheo: IBacTienHoNgheo = { id: 54044 };
        hoaDon.bacHoNgheo = bacHoNgheo;

        const bacHoNgheoCollection: IBacTienHoNgheo[] = [{ id: 90586 }];
        spyOn(bacTienHoNgheoService, 'query').and.returnValue(of(new HttpResponse({ body: bacHoNgheoCollection })));
        const expectedCollection: IBacTienHoNgheo[] = [bacHoNgheo, ...bacHoNgheoCollection];
        spyOn(bacTienHoNgheoService, 'addBacTienHoNgheoToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(bacTienHoNgheoService.query).toHaveBeenCalled();
        expect(bacTienHoNgheoService.addBacTienHoNgheoToCollectionIfMissing).toHaveBeenCalledWith(bacHoNgheoCollection, bacHoNgheo);
        expect(comp.bacHoNgheosCollection).toEqual(expectedCollection);
      });

      it('Should call bacHoThuong query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const bacHoThuong: IBacTienHoThuong = { id: 61753 };
        hoaDon.bacHoThuong = bacHoThuong;

        const bacHoThuongCollection: IBacTienHoThuong[] = [{ id: 64885 }];
        spyOn(bacTienHoThuongService, 'query').and.returnValue(of(new HttpResponse({ body: bacHoThuongCollection })));
        const expectedCollection: IBacTienHoThuong[] = [bacHoThuong, ...bacHoThuongCollection];
        spyOn(bacTienHoThuongService, 'addBacTienHoThuongToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(bacTienHoThuongService.query).toHaveBeenCalled();
        expect(bacTienHoThuongService.addBacTienHoThuongToCollectionIfMissing).toHaveBeenCalledWith(bacHoThuongCollection, bacHoThuong);
        expect(comp.bacHoThuongsCollection).toEqual(expectedCollection);
      });

      it('Should call giadinh query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const giadinh: IHoGiaDinh = { id: 97466 };
        hoaDon.giadinh = giadinh;

        const giadinhCollection: IHoGiaDinh[] = [{ id: 52348 }];
        spyOn(hoGiaDinhService, 'query').and.returnValue(of(new HttpResponse({ body: giadinhCollection })));
        const expectedCollection: IHoGiaDinh[] = [giadinh, ...giadinhCollection];
        spyOn(hoGiaDinhService, 'addHoGiaDinhToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(hoGiaDinhService.query).toHaveBeenCalled();
        expect(hoGiaDinhService.addHoGiaDinhToCollectionIfMissing).toHaveBeenCalledWith(giadinhCollection, giadinh);
        expect(comp.giadinhsCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const thue: IThue = { id: 64338 };
        hoaDon.thue = thue;
        const bacHoNgheo: IBacTienHoNgheo = { id: 67684 };
        hoaDon.bacHoNgheo = bacHoNgheo;
        const bacHoThuong: IBacTienHoThuong = { id: 82182 };
        hoaDon.bacHoThuong = bacHoThuong;
        const giadinh: IHoGiaDinh = { id: 66633 };
        hoaDon.giadinh = giadinh;

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hoaDon));
        expect(comp.thuesCollection).toContain(thue);
        expect(comp.bacHoNgheosCollection).toContain(bacHoNgheo);
        expect(comp.bacHoThuongsCollection).toContain(bacHoThuong);
        expect(comp.giadinhsCollection).toContain(giadinh);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hoaDon = { id: 123 };
        spyOn(hoaDonService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hoaDon }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hoaDonService.update).toHaveBeenCalledWith(hoaDon);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hoaDon = new HoaDon();
        spyOn(hoaDonService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hoaDon }));
        saveSubject.complete();

        // THEN
        expect(hoaDonService.create).toHaveBeenCalledWith(hoaDon);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const hoaDon = { id: 123 };
        spyOn(hoaDonService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hoaDonService.update).toHaveBeenCalledWith(hoaDon);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackThueById', () => {
        it('Should return tracked Thue primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackThueById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackBacTienHoNgheoById', () => {
        it('Should return tracked BacTienHoNgheo primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackBacTienHoNgheoById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackBacTienHoThuongById', () => {
        it('Should return tracked BacTienHoThuong primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackBacTienHoThuongById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackHoGiaDinhById', () => {
        it('Should return tracked HoGiaDinh primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHoGiaDinhById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
