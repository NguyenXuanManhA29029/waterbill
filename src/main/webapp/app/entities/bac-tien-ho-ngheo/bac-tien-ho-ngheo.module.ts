import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { BacTienHoNgheoComponent } from './list/bac-tien-ho-ngheo.component';
import { BacTienHoNgheoDetailComponent } from './detail/bac-tien-ho-ngheo-detail.component';
import { BacTienHoNgheoUpdateComponent } from './update/bac-tien-ho-ngheo-update.component';
import { BacTienHoNgheoDeleteDialogComponent } from './delete/bac-tien-ho-ngheo-delete-dialog.component';
import { BacTienHoNgheoRoutingModule } from './route/bac-tien-ho-ngheo-routing.module';

@NgModule({
  imports: [SharedModule, BacTienHoNgheoRoutingModule],
  declarations: [
    BacTienHoNgheoComponent,
    BacTienHoNgheoDetailComponent,
    BacTienHoNgheoUpdateComponent,
    BacTienHoNgheoDeleteDialogComponent,
  ],
  entryComponents: [BacTienHoNgheoDeleteDialogComponent],
})
export class BacTienHoNgheoModule {}
