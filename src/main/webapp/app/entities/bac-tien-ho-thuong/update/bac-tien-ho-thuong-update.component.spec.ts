jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';
import { IBacTienHoThuong, BacTienHoThuong } from '../bac-tien-ho-thuong.model';

import { BacTienHoThuongUpdateComponent } from './bac-tien-ho-thuong-update.component';

describe('Component Tests', () => {
  describe('BacTienHoThuong Management Update Component', () => {
    let comp: BacTienHoThuongUpdateComponent;
    let fixture: ComponentFixture<BacTienHoThuongUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let bacTienHoThuongService: BacTienHoThuongService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BacTienHoThuongUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BacTienHoThuongUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BacTienHoThuongUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      bacTienHoThuongService = TestBed.inject(BacTienHoThuongService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const bacTienHoThuong: IBacTienHoThuong = { id: 456 };

        activatedRoute.data = of({ bacTienHoThuong });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(bacTienHoThuong));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bacTienHoThuong = { id: 123 };
        spyOn(bacTienHoThuongService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bacTienHoThuong });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bacTienHoThuong }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(bacTienHoThuongService.update).toHaveBeenCalledWith(bacTienHoThuong);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bacTienHoThuong = new BacTienHoThuong();
        spyOn(bacTienHoThuongService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bacTienHoThuong });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: bacTienHoThuong }));
        saveSubject.complete();

        // THEN
        expect(bacTienHoThuongService.create).toHaveBeenCalledWith(bacTienHoThuong);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const bacTienHoThuong = { id: 123 };
        spyOn(bacTienHoThuongService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ bacTienHoThuong });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(bacTienHoThuongService.update).toHaveBeenCalledWith(bacTienHoThuong);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
