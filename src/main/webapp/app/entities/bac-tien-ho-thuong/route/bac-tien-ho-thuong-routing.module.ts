import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BacTienHoThuongComponent } from '../list/bac-tien-ho-thuong.component';
import { BacTienHoThuongDetailComponent } from '../detail/bac-tien-ho-thuong-detail.component';
import { BacTienHoThuongUpdateComponent } from '../update/bac-tien-ho-thuong-update.component';
import { BacTienHoThuongRoutingResolveService } from './bac-tien-ho-thuong-routing-resolve.service';

const bacTienHoThuongRoute: Routes = [
  {
    path: '',
    component: BacTienHoThuongComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BacTienHoThuongDetailComponent,
    resolve: {
      bacTienHoThuong: BacTienHoThuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BacTienHoThuongUpdateComponent,
    resolve: {
      bacTienHoThuong: BacTienHoThuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BacTienHoThuongUpdateComponent,
    resolve: {
      bacTienHoThuong: BacTienHoThuongRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bacTienHoThuongRoute)],
  exports: [RouterModule],
})
export class BacTienHoThuongRoutingModule {}
