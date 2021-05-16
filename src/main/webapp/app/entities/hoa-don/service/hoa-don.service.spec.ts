import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IHoaDon, HoaDon } from '../hoa-don.model';

import { HoaDonService } from './hoa-don.service';

describe('Service Tests', () => {
  describe('HoaDon Service', () => {
    let service: HoaDonService;
    let httpMock: HttpTestingController;
    let elemDefault: IHoaDon;
    let expectedResult: IHoaDon | IHoaDon[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HoaDonService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        tenChuHo: 'AAAAAAA',
        thangSuDung: 0,
        chiSoMoi: 0,
        chiSoCu: 0,
        soNuoc: 0,
        thanhTien: 0,
        tienThue: 0,
        tongTien: 0,
        ngayThanhToan: currentDate,
        trangThaiThanhToan: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            ngayThanhToan: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HoaDon', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            ngayThanhToan: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayThanhToan: currentDate,
          },
          returnedFromService
        );

        service.create(new HoaDon()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HoaDon', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenChuHo: 'BBBBBB',
            thangSuDung: 1,
            chiSoMoi: 1,
            chiSoCu: 1,
            soNuoc: 1,
            thanhTien: 1,
            tienThue: 1,
            tongTien: 1,
            ngayThanhToan: currentDate.format(DATE_TIME_FORMAT),
            trangThaiThanhToan: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayThanhToan: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a HoaDon', () => {
        const patchObject = Object.assign(
          {
            tenChuHo: 'BBBBBB',
            thangSuDung: 1,
            chiSoCu: 1,
            tienThue: 1,
          },
          new HoaDon()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            ngayThanhToan: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HoaDon', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenChuHo: 'BBBBBB',
            thangSuDung: 1,
            chiSoMoi: 1,
            chiSoCu: 1,
            soNuoc: 1,
            thanhTien: 1,
            tienThue: 1,
            tongTien: 1,
            ngayThanhToan: currentDate.format(DATE_TIME_FORMAT),
            trangThaiThanhToan: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            ngayThanhToan: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HoaDon', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHoaDonToCollectionIfMissing', () => {
        it('should add a HoaDon to an empty array', () => {
          const hoaDon: IHoaDon = { id: 123 };
          expectedResult = service.addHoaDonToCollectionIfMissing([], hoaDon);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hoaDon);
        });

        it('should not add a HoaDon to an array that contains it', () => {
          const hoaDon: IHoaDon = { id: 123 };
          const hoaDonCollection: IHoaDon[] = [
            {
              ...hoaDon,
            },
            { id: 456 },
          ];
          expectedResult = service.addHoaDonToCollectionIfMissing(hoaDonCollection, hoaDon);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HoaDon to an array that doesn't contain it", () => {
          const hoaDon: IHoaDon = { id: 123 };
          const hoaDonCollection: IHoaDon[] = [{ id: 456 }];
          expectedResult = service.addHoaDonToCollectionIfMissing(hoaDonCollection, hoaDon);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hoaDon);
        });

        it('should add only unique HoaDon to an array', () => {
          const hoaDonArray: IHoaDon[] = [{ id: 123 }, { id: 456 }, { id: 76047 }];
          const hoaDonCollection: IHoaDon[] = [{ id: 123 }];
          expectedResult = service.addHoaDonToCollectionIfMissing(hoaDonCollection, ...hoaDonArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const hoaDon: IHoaDon = { id: 123 };
          const hoaDon2: IHoaDon = { id: 456 };
          expectedResult = service.addHoaDonToCollectionIfMissing([], hoaDon, hoaDon2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hoaDon);
          expect(expectedResult).toContain(hoaDon2);
        });

        it('should accept null and undefined values', () => {
          const hoaDon: IHoaDon = { id: 123 };
          expectedResult = service.addHoaDonToCollectionIfMissing([], null, hoaDon, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hoaDon);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
