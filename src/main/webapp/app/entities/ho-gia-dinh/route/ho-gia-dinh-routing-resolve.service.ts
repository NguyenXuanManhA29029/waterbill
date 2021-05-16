import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHoGiaDinh, HoGiaDinh } from '../ho-gia-dinh.model';
import { HoGiaDinhService } from '../service/ho-gia-dinh.service';

@Injectable({ providedIn: 'root' })
export class HoGiaDinhRoutingResolveService implements Resolve<IHoGiaDinh> {
  constructor(protected service: HoGiaDinhService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IHoGiaDinh> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((hoGiaDinh: HttpResponse<HoGiaDinh>) => {
          if (hoGiaDinh.body) {
            return of(hoGiaDinh.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new HoGiaDinh());
  }
}
