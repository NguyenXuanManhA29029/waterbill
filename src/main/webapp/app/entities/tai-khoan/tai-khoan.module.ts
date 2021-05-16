import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaiKhoanComponent } from './list/tai-khoan.component';
import { TaiKhoanDetailComponent } from './detail/tai-khoan-detail.component';
import { TaiKhoanUpdateComponent } from './update/tai-khoan-update.component';
import { TaiKhoanDeleteDialogComponent } from './delete/tai-khoan-delete-dialog.component';
import { TaiKhoanRoutingModule } from './route/tai-khoan-routing.module';

@NgModule({
  imports: [SharedModule, TaiKhoanRoutingModule],
  declarations: [TaiKhoanComponent, TaiKhoanDetailComponent, TaiKhoanUpdateComponent, TaiKhoanDeleteDialogComponent],
  entryComponents: [TaiKhoanDeleteDialogComponent],
})
export class TaiKhoanModule {}
