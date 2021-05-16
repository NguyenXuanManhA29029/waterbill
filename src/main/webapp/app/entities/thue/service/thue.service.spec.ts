import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IThue, Thue } from '../thue.model';

import { ThueService } from './thue.service';

describe('Service Tests', () => {
  describe('Thue Service', () => {
    let service: ThueService;
    let httpMock: HttpTestingController;
    let elemDefault: IThue;
    let expectedResult: IThue | IThue[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ThueService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        tenThue: 'AAAAAAA',
        giaTriThue: 0,
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

      it('should create a Thue', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Thue()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Thue', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenThue: 'BBBBBB',
            giaTriThue: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Thue', () => {
        const patchObject = Object.assign(
          {
            tenThue: 'BBBBBB',
          },
          new Thue()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Thue', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            tenThue: 'BBBBBB',
            giaTriThue: 1,
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

      it('should delete a Thue', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addThueToCollectionIfMissing', () => {
        it('should add a Thue to an empty array', () => {
          const thue: IThue = { id: 123 };
          expectedResult = service.addThueToCollectionIfMissing([], thue);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(thue);
        });

        it('should not add a Thue to an array that contains it', () => {
          const thue: IThue = { id: 123 };
          const thueCollection: IThue[] = [
            {
              ...thue,
            },
            { id: 456 },
          ];
          expectedResult = service.addThueToCollectionIfMissing(thueCollection, thue);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Thue to an array that doesn't contain it", () => {
          const thue: IThue = { id: 123 };
          const thueCollection: IThue[] = [{ id: 456 }];
          expectedResult = service.addThueToCollectionIfMissing(thueCollection, thue);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(thue);
        });

        it('should add only unique Thue to an array', () => {
          const thueArray: IThue[] = [{ id: 123 }, { id: 456 }, { id: 85671 }];
          const thueCollection: IThue[] = [{ id: 123 }];
          expectedResult = service.addThueToCollectionIfMissing(thueCollection, ...thueArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const thue: IThue = { id: 123 };
          const thue2: IThue = { id: 456 };
          expectedResult = service.addThueToCollectionIfMissing([], thue, thue2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(thue);
          expect(expectedResult).toContain(thue2);
        });

        it('should accept null and undefined values', () => {
          const thue: IThue = { id: 123 };
          expectedResult = service.addThueToCollectionIfMissing([], null, thue, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(thue);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
