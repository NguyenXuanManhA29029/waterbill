import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IThue, Thue } from '../thue.model';
import { ThueService } from '../service/thue.service';

@Injectable({ providedIn: 'root' })
export class ThueRoutingResolveService implements Resolve<IThue> {
  constructor(protected service: ThueService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThue> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((thue: HttpResponse<Thue>) => {
          if (thue.body) {
            return of(thue.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Thue());
  }
}
