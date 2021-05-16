jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBacTienHoThuong, BacTienHoThuong } from '../bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';

import { BacTienHoThuongRoutingResolveService } from './bac-tien-ho-thuong-routing-resolve.service';

describe('Service Tests', () => {
  describe('BacTienHoThuong routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BacTienHoThuongRoutingResolveService;
    let service: BacTienHoThuongService;
    let resultBacTienHoThuong: IBacTienHoThuong | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BacTienHoThuongRoutingResolveService);
      service = TestBed.inject(BacTienHoThuongService);
      resultBacTienHoThuong = undefined;
    });

    describe('resolve', () => {
      it('should return IBacTienHoThuong returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBacTienHoThuong = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBacTienHoThuong).toEqual({ id: 123 });
      });

      it('should return new IBacTienHoThuong if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBacTienHoThuong = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultBacTienHoThuong).toEqual(new BacTienHoThuong());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBacTienHoThuong = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBacTienHoThuong).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
