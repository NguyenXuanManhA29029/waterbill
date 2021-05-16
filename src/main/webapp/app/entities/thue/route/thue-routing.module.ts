import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ThueComponent } from '../list/thue.component';
import { ThueDetailComponent } from '../detail/thue-detail.component';
import { ThueUpdateComponent } from '../update/thue-update.component';
import { ThueRoutingResolveService } from './thue-routing-resolve.service';

const thueRoute: Routes = [
  {
    path: '',
    component: ThueComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ThueDetailComponent,
    resolve: {
      thue: ThueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ThueUpdateComponent,
    resolve: {
      thue: ThueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ThueUpdateComponent,
    resolve: {
      thue: ThueRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(thueRoute)],
  exports: [RouterModule],
})
export class ThueRoutingModule {}
