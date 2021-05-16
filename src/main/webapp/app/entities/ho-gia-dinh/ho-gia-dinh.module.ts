import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { HoGiaDinhComponent } from './list/ho-gia-dinh.component';
import { HoGiaDinhDetailComponent } from './detail/ho-gia-dinh-detail.component';
import { HoGiaDinhUpdateComponent } from './update/ho-gia-dinh-update.component';
import { HoGiaDinhDeleteDialogComponent } from './delete/ho-gia-dinh-delete-dialog.component';
import { HoGiaDinhRoutingModule } from './route/ho-gia-dinh-routing.module';

@NgModule({
  imports: [SharedModule, HoGiaDinhRoutingModule],
  declarations: [HoGiaDinhComponent, HoGiaDinhDetailComponent, HoGiaDinhUpdateComponent, HoGiaDinhDeleteDialogComponent],
  entryComponents: [HoGiaDinhDeleteDialogComponent],
})
export class HoGiaDinhModule {}
