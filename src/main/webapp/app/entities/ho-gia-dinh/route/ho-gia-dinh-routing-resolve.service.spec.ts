jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IHoGiaDinh, HoGiaDinh } from '../ho-gia-dinh.model';
import { HoGiaDinhService } from '../service/ho-gia-dinh.service';

import { HoGiaDinhRoutingResolveService } from './ho-gia-dinh-routing-resolve.service';

describe('Service Tests', () => {
  describe('HoGiaDinh routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: HoGiaDinhRoutingResolveService;
    let service: HoGiaDinhService;
    let resultHoGiaDinh: IHoGiaDinh | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(HoGiaDinhRoutingResolveService);
      service = TestBed.inject(HoGiaDinhService);
      resultHoGiaDinh = undefined;
    });

    describe('resolve', () => {
      it('should return IHoGiaDinh returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHoGiaDinh = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHoGiaDinh).toEqual({ id: 123 });
      });

      it('should return new IHoGiaDinh if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHoGiaDinh = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultHoGiaDinh).toEqual(new HoGiaDinh());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultHoGiaDinh = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultHoGiaDinh).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
