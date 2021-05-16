import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBacTienHoThuong, BacTienHoThuong } from '../bac-tien-ho-thuong.model';

import { BacTienHoThuongService } from './bac-tien-ho-thuong.service';

describe('Service Tests', () => {
  describe('BacTienHoThuong Service', () => {
    let service: BacTienHoThuongService;
    let httpMock: HttpTestingController;
    let elemDefault: IBacTienHoThuong;
    let expectedResult: IBacTienHoThuong | IBacTienHoThuong[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BacTienHoThuongService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenBac: 'AAAAAAA',
        giaTriBac: 0,
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

      it('should create a BacTienHoThuong', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BacTienHoThuong()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BacTienHoThuong', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenBac: 'BBBBBB',
            giaTriBac: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a BacTienHoThuong', () => {
        const patchObject = Object.assign({}, new BacTienHoThuong());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BacTienHoThuong', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenBac: 'BBBBBB',
            giaTriBac: 1,
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

      it('should delete a BacTienHoThuong', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBacTienHoThuongToCollectionIfMissing', () => {
        it('should add a BacTienHoThuong to an empty array', () => {
          const bacTienHoThuong: IBacTienHoThuong = { id: 123 };
          expectedResult = service.addBacTienHoThuongToCollectionIfMissing([], bacTienHoThuong);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bacTienHoThuong);
        });

        it('should not add a BacTienHoThuong to an array that contains it', () => {
          const bacTienHoThuong: IBacTienHoThuong = { id: 123 };
          const bacTienHoThuongCollection: IBacTienHoThuong[] = [
            {
              ...bacTienHoThuong,
            },
            { id: 456 },
          ];
          expectedResult = service.addBacTienHoThuongToCollectionIfMissing(bacTienHoThuongCollection, bacTienHoThuong);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a BacTienHoThuong to an array that doesn't contain it", () => {
          const bacTienHoThuong: IBacTienHoThuong = { id: 123 };
          const bacTienHoThuongCollection: IBacTienHoThuong[] = [{ id: 456 }];
          expectedResult = service.addBacTienHoThuongToCollectionIfMissing(bacTienHoThuongCollection, bacTienHoThuong);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bacTienHoThuong);
        });

        it('should add only unique BacTienHoThuong to an array', () => {
          const bacTienHoThuongArray: IBacTienHoThuong[] = [{ id: 123 }, { id: 456 }, { id: 18816 }];
          const bacTienHoThuongCollection: IBacTienHoThuong[] = [{ id: 123 }];
          expectedResult = service.addBacTienHoThuongToCollectionIfMissing(bacTienHoThuongCollection, ...bacTienHoThuongArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const bacTienHoThuong: IBacTienHoThuong = { id: 123 };
          const bacTienHoThuong2: IBacTienHoThuong = { id: 456 };
          expectedResult = service.addBacTienHoThuongToCollectionIfMissing([], bacTienHoThuong, bacTienHoThuong2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bacTienHoThuong);
          expect(expectedResult).toContain(bacTienHoThuong2);
        });

        it('should accept null and undefined values', () => {
          const bacTienHoThuong: IBacTienHoThuong = { id: 123 };
          expectedResult = service.addBacTienHoThuongToCollectionIfMissing([], null, bacTienHoThuong, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bacTienHoThuong);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
