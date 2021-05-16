import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BacTienHoThuongComponent } from './list/bac-tien-ho-thuong.component';
import { BacTienHoThuongDetailComponent } from './detail/bac-tien-ho-thuong-detail.component';
import { BacTienHoThuongUpdateComponent } from './update/bac-tien-ho-thuong-update.component';
import { BacTienHoThuongDeleteDialogComponent } from './delete/bac-tien-ho-thuong-delete-dialog.component';
import { BacTienHoThuongRoutingModule } from './route/bac-tien-ho-thuong-routing.module';

@NgModule({
  imports: [SharedModule, BacTienHoThuongRoutingModule],
  declarations: [
    BacTienHoThuongComponent,
    BacTienHoThuongDetailComponent,
    BacTienHoThuongUpdateComponent,
    BacTienHoThuongDeleteDialogComponent,
  ],
  entryComponents: [BacTienHoThuongDeleteDialogComponent],
})
export class BacTienHoThuongModule {}
