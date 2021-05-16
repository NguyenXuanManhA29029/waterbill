import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBacTienHoNgheo, BacTienHoNgheo } from '../bac-tien-ho-ngheo.model';
import { BacTienHoNgheoService } from '../service/bac-tien-ho-ngheo.service';

@Injectable({ providedIn: 'root' })
export class BacTienHoNgheoRoutingResolveService implements Resolve<IBacTienHoNgheo> {
  constructor(protected service: BacTienHoNgheoService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBacTienHoNgheo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bacTienHoNgheo: HttpResponse<BacTienHoNgheo>) => {
          if (bacTienHoNgheo.body) {
            return of(bacTienHoNgheo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BacTienHoNgheo());
  }
}
