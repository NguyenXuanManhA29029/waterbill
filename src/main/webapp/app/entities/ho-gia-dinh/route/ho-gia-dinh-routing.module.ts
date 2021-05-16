import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HoGiaDinhComponent } from '../list/ho-gia-dinh.component';
import { HoGiaDinhDetailComponent } from '../detail/ho-gia-dinh-detail.component';
import { HoGiaDinhUpdateComponent } from '../update/ho-gia-dinh-update.component';
import { HoGiaDinhRoutingResolveService } from './ho-gia-dinh-routing-resolve.service';

const hoGiaDinhRoute: Routes = [
  {
    path: '',
    component: HoGiaDinhComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HoGiaDinhDetailComponent,
    resolve: {
      hoGiaDinh: HoGiaDinhRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HoGiaDinhUpdateComponent,
    resolve: {
      hoGiaDinh: HoGiaDinhRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HoGiaDinhUpdateComponent,
    resolve: {
      hoGiaDinh: HoGiaDinhRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(hoGiaDinhRoute)],
  exports: [RouterModule],
})
export class HoGiaDinhRoutingModule {}
