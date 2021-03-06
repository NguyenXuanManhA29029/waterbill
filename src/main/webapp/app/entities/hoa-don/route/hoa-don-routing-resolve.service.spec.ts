jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHoaDon, HoaDon } from '../hoa-don.model';
import { HoaDonService } from '../service/hoa-don.service';

import { HoaDonRoutingResolveService } from './hoa-don-routing-resolve.service';

describe('Service Tests', () => {
  describe('HoaDon routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HoaDonRoutingResolveService;
    let service: HoaDonService;
    let resultHoaDon: IHoaDon | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HoaDonRoutingResolveService);
      service = TestBed.inject(HoaDonService);
      resultHoaDon = undefined;
    });

    describe('resolve', () => {
      it('should return IHoaDon returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHoaDon = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHoaDon).toEqual({ id: 123 });
      });

      it('should return new IHoaDon if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHoaDon = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHoaDon).toEqual(new HoaDon());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHoaDon = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHoaDon).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
