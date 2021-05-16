import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { BacTienHoNgheoComponent } from '../list/bac-tien-ho-ngheo.component';
import { BacTienHoNgheoDetailComponent } from '../detail/bac-tien-ho-ngheo-detail.component';
import { BacTienHoNgheoUpdateComponent } from '../update/bac-tien-ho-ngheo-update.component';
import { BacTienHoNgheoRoutingResolveService } from './bac-tien-ho-ngheo-routing-resolve.service';

const bacTienHoNgheoRoute: Routes = [
  {
    path: '',
    component: BacTienHoNgheoComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BacTienHoNgheoDetailComponent,
    resolve: {
      bacTienHoNgheo: BacTienHoNgheoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BacTienHoNgheoUpdateComponent,
    resolve: {
      bacTienHoNgheo: BacTienHoNgheoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BacTienHoNgheoUpdateComponent,
    resolve: {
      bacTienHoNgheo: BacTienHoNgheoRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(bacTienHoNgheoRoute)],
  exports: [RouterModule],
})
export class BacTienHoNgheoRoutingModule {}
