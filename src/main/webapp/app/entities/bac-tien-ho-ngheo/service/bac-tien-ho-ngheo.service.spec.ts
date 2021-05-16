import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBacTienHoNgheo, BacTienHoNgheo } from '../bac-tien-ho-ngheo.model';

import { BacTienHoNgheoService } from './bac-tien-ho-ngheo.service';

describe('Service Tests', () => {
  describe('BacTienHoNgheo Service', () => {
    let service: BacTienHoNgheoService;
    let httpMock: HttpTestingController;
    let elemDefault: IBacTienHoNgheo;
    let expectedResult: IBacTienHoNgheo | IBacTienHoNgheo[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BacTienHoNgheoService);
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

      it('should create a BacTienHoNgheo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BacTienHoNgheo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BacTienHoNgheo', () => {
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

      it('should partial update a BacTienHoNgheo', () => {
        const patchObject = Object.assign(
          {
            giaTriBac: 1,
          },
          new BacTienHoNgheo()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BacTienHoNgheo', () => {
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

      it('should delete a BacTienHoNgheo', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBacTienHoNgheoToCollectionIfMissing', () => {
        it('should add a BacTienHoNgheo to an empty array', () => {
          const bacTienHoNgheo: IBacTienHoNgheo = { id: 123 };
          expectedResult = service.addBacTienHoNgheoToCollectionIfMissing([], bacTienHoNgheo);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bacTienHoNgheo);
        });

        it('should not add a BacTienHoNgheo to an array that contains it', () => {
          const bacTienHoNgheo: IBacTienHoNgheo = { id: 123 };
          const bacTienHoNgheoCollection: IBacTienHoNgheo[] = [
            {
              ...bacTienHoNgheo,
            },
            { id: 456 },
          ];
          expectedResult = service.addBacTienHoNgheoToCollectionIfMissing(bacTienHoNgheoCollection, bacTienHoNgheo);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a BacTienHoNgheo to an array that doesn't contain it", () => {
          const bacTienHoNgheo: IBacTienHoNgheo = { id: 123 };
          const bacTienHoNgheoCollection: IBacTienHoNgheo[] = [{ id: 456 }];
          expectedResult = service.addBacTienHoNgheoToCollectionIfMissing(bacTienHoNgheoCollection, bacTienHoNgheo);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bacTienHoNgheo);
        });

        it('should add only unique BacTienHoNgheo to an array', () => {
          const bacTienHoNgheoArray: IBacTienHoNgheo[] = [{ id: 123 }, { id: 456 }, { id: 27051 }];
          const bacTienHoNgheoCollection: IBacTienHoNgheo[] = [{ id: 123 }];
          expectedResult = service.addBacTienHoNgheoToCollectionIfMissing(bacTienHoNgheoCollection, ...bacTienHoNgheoArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const bacTienHoNgheo: IBacTienHoNgheo = { id: 123 };
          const bacTienHoNgheo2: IBacTienHoNgheo = { id: 456 };
          expectedResult = service.addBacTienHoNgheoToCollectionIfMissing([], bacTienHoNgheo, bacTienHoNgheo2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(bacTienHoNgheo);
          expect(expectedResult).toContain(bacTienHoNgheo2);
        });

        it('should accept null and undefined values', () => {
          const bacTienHoNgheo: IBacTienHoNgheo = { id: 123 };
          expectedResult = service.addBacTienHoNgheoToCollectionIfMissing([], null, bacTienHoNgheo, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(bacTienHoNgheo);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
