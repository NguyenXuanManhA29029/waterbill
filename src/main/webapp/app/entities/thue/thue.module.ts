import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ThueComponent } from './list/thue.component';
import { ThueDetailComponent } from './detail/thue-detail.component';
import { ThueUpdateComponent } from './update/thue-update.component';
import { ThueDeleteDialogComponent } from './delete/thue-delete-dialog.component';
import { ThueRoutingModule } from './route/thue-routing.module';

@NgModule({
  imports: [SharedModule, ThueRoutingModule],
  declarations: [ThueComponent, ThueDetailComponent, ThueUpdateComponent, ThueDeleteDialogComponent],
  entryComponents: [ThueDeleteDialogComponent],
})
export class ThueModule {}
