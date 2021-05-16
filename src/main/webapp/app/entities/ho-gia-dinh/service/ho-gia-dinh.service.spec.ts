import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHoGiaDinh, HoGiaDinh } from '../ho-gia-dinh.model';

import { HoGiaDinhService } from './ho-gia-dinh.service';

describe('Service Tests', () => {
  describe('HoGiaDinh Service', () => {
    let service: HoGiaDinhService;
    let httpMock: HttpTestingController;
    let elemDefault: IHoGiaDinh;
    let expectedResult: IHoGiaDinh | IHoGiaDinh[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HoGiaDinhService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenChuHo: 'AAAAAAA',
        maHo: 'AAAAAAA',
        soCanCuoc: 'AAAAAAA',
        loaiHo: 'AAAAAAA',
        soHoNgheo: 'AAAAAAA',
        email: 'AAAAAAA',
        sdt: 'AAAAAAA',
        diaChi: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HoGiaDinh', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new HoGiaDinh()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HoGiaDinh', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenChuHo: 'BBBBBB',
            maHo: 'BBBBBB',
            soCanCuoc: 'BBBBBB',
            loaiHo: 'BBBBBB',
            soHoNgheo: 'BBBBBB',
            email: 'BBBBBB',
            sdt: 'BBBBBB',
            diaChi: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a HoGiaDinh', () => {
        const patchObject = Object.assign(
          {
            tenChuHo: 'BBBBBB',
          },
          new HoGiaDinh()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HoGiaDinh', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenChuHo: 'BBBBBB',
            maHo: 'BBBBBB',
            soCanCuoc: 'BBBBBB',
            loaiHo: 'BBBBBB',
            soHoNgheo: 'BBBBBB',
            email: 'BBBBBB',
            sdt: 'BBBBBB',
            diaChi: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HoGiaDinh', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHoGiaDinhToCollectionIfMissing', () => {
        it('should add a HoGiaDinh to an empty array', () => {
          const hoGiaDinh: IHoGiaDinh = { id: 123 };
          expectedResult = service.addHoGiaDinhToCollectionIfMissing([], hoGiaDinh);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hoGiaDinh);
        });

        it('should not add a HoGiaDinh to an array that contains it', () => {
          const hoGiaDinh: IHoGiaDinh = { id: 123 };
          const hoGiaDinhCollection: IHoGiaDinh[] = [
            {
              ...hoGiaDinh,
            },
            { id: 456 },
          ];
          expectedResult = service.addHoGiaDinhToCollectionIfMissing(hoGiaDinhCollection, hoGiaDinh);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HoGiaDinh to an array that doesn't contain it", () => {
          const hoGiaDinh: IHoGiaDinh = { id: 123 };
          const hoGiaDinhCollection: IHoGiaDinh[] = [{ id: 456 }];
          expectedResult = service.addHoGiaDinhToCollectionIfMissing(hoGiaDinhCollection, hoGiaDinh);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hoGiaDinh);
        });

        it('should add only unique HoGiaDinh to an array', () => {
          const hoGiaDinhArray: IHoGiaDinh[] = [{ id: 123 }, { id: 456 }, { id: 94119 }];
          const hoGiaDinhCollection: IHoGiaDinh[] = [{ id: 123 }];
          expectedResult = service.addHoGiaDinhToCollectionIfMissing(hoGiaDinhCollection, ...hoGiaDinhArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const hoGiaDinh: IHoGiaDinh = { id: 123 };
          const hoGiaDinh2: IHoGiaDinh = { id: 456 };
          expectedResult = service.addHoGiaDinhToCollectionIfMissing([], hoGiaDinh, hoGiaDinh2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hoGiaDinh);
          expect(expectedResult).toContain(hoGiaDinh2);
        });

        it('should accept null and undefined values', () => {
          const hoGiaDinh: IHoGiaDinh = { id: 123 };
          expectedResult = service.addHoGiaDinhToCollectionIfMissing([], null, hoGiaDinh, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hoGiaDinh);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
