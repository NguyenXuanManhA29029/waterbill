import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITaiKhoan, TaiKhoan } from '../tai-khoan.model';

import { TaiKhoanService } from './tai-khoan.service';

describe('Service Tests', () => {
  describe('TaiKhoan Service', () => {
    let service: TaiKhoanService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaiKhoan;
    let expectedResult: ITaiKhoan | ITaiKhoan[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaiKhoanService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        userName: 'AAAAAAA',
        passWord: 'AAAAAAA',
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

      it('should create a TaiKhoan', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TaiKhoan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaiKhoan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            userName: 'BBBBBB',
            passWord: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TaiKhoan', () => {
        const patchObject = Object.assign(
          {
            userName: 'BBBBBB',
          },
          new TaiKhoan()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TaiKhoan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            userName: 'BBBBBB',
            passWord: 'BBBBBB',
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

      it('should delete a TaiKhoan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaiKhoanToCollectionIfMissing', () => {
        it('should add a TaiKhoan to an empty array', () => {
          const taiKhoan: ITaiKhoan = { id: 123 };
          expectedResult = service.addTaiKhoanToCollectionIfMissing([], taiKhoan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taiKhoan);
        });

        it('should not add a TaiKhoan to an array that contains it', () => {
          const taiKhoan: ITaiKhoan = { id: 123 };
          const taiKhoanCollection: ITaiKhoan[] = [
            {
              ...taiKhoan,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaiKhoanToCollectionIfMissing(taiKhoanCollection, taiKhoan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaiKhoan to an array that doesn't contain it", () => {
          const taiKhoan: ITaiKhoan = { id: 123 };
          const taiKhoanCollection: ITaiKhoan[] = [{ id: 456 }];
          expectedResult = service.addTaiKhoanToCollectionIfMissing(taiKhoanCollection, taiKhoan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taiKhoan);
        });

        it('should add only unique TaiKhoan to an array', () => {
          const taiKhoanArray: ITaiKhoan[] = [{ id: 123 }, { id: 456 }, { id: 76770 }];
          const taiKhoanCollection: ITaiKhoan[] = [{ id: 123 }];
          expectedResult = service.addTaiKhoanToCollectionIfMissing(taiKhoanCollection, ...taiKhoanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taiKhoan: ITaiKhoan = { id: 123 };
          const taiKhoan2: ITaiKhoan = { id: 456 };
          expectedResult = service.addTaiKhoanToCollectionIfMissing([], taiKhoan, taiKhoan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taiKhoan);
          expect(expectedResult).toContain(taiKhoan2);
        });

        it('should accept null and undefined values', () => {
          const taiKhoan: ITaiKhoan = { id: 123 };
          expectedResult = service.addTaiKhoanToCollectionIfMissing([], null, taiKhoan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taiKhoan);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
