jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITaiKhoan, TaiKhoan } from '../tai-khoan.model';
import { TaiKhoanService } from '../service/tai-khoan.service';

import { TaiKhoanRoutingResolveService } from './tai-khoan-routing-resolve.service';

describe('Service Tests', () => {
  describe('TaiKhoan routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: TaiKhoanRoutingResolveService;
    let service: TaiKhoanService;
    let resultTaiKhoan: ITaiKhoan | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(TaiKhoanRoutingResolveService);
      service = TestBed.inject(TaiKhoanService);
      resultTaiKhoan = undefined;
    });

    describe('resolve', () => {
      it('should return ITaiKhoan returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaiKhoan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaiKhoan).toEqual({ id: 123 });
      });

      it('should return new ITaiKhoan if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaiKhoan = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultTaiKhoan).toEqual(new TaiKhoan());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultTaiKhoan = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultTaiKhoan).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
