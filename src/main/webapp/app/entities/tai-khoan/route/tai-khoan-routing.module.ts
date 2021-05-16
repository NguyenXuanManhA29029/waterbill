import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TaiKhoanComponent } from '../list/tai-khoan.component';
import { TaiKhoanDetailComponent } from '../detail/tai-khoan-detail.component';
import { TaiKhoanUpdateComponent } from '../update/tai-khoan-update.component';
import { TaiKhoanRoutingResolveService } from './tai-khoan-routing-resolve.service';

const taiKhoanRoute: Routes = [
  {
    path: '',
    component: TaiKhoanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TaiKhoanDetailComponent,
    resolve: {
      taiKhoan: TaiKhoanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TaiKhoanUpdateComponent,
    resolve: {
      taiKhoan: TaiKhoanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TaiKhoanUpdateComponent,
    resolve: {
      taiKhoan: TaiKhoanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(taiKhoanRoute)],
  exports: [RouterModule],
})
export class TaiKhoanRoutingModule {}
