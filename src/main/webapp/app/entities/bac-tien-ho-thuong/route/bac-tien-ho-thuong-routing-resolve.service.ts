import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBacTienHoThuong, BacTienHoThuong } from '../bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';

@Injectable({ providedIn: 'root' })
export class BacTienHoThuongRoutingResolveService implements Resolve<IBacTienHoThuong> {
  constructor(protected service: BacTienHoThuongService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBacTienHoThuong> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((bacTienHoThuong: HttpResponse<BacTienHoThuong>) => {
          if (bacTienHoThuong.body) {
            return of(bacTienHoThuong.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BacTienHoThuong());
  }
}
