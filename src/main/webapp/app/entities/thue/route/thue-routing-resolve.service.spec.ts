jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IThue, Thue } from '../thue.model';
import { ThueService } from '../service/thue.service';

import { ThueRoutingResolveService } from './thue-routing-resolve.service';

describe('Service Tests', () => {
  describe('Thue routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ThueRoutingResolveService;
    let service: ThueService;
    let resultThue: IThue | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ThueRoutingResolveService);
      service = TestBed.inject(ThueService);
      resultThue = undefined;
    });

    describe('resolve', () => {
      it('should return IThue returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThue = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultThue).toEqual({ id: 123 });
      });

      it('should return new IThue if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThue = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultThue).toEqual(new Thue());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultThue = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultThue).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
