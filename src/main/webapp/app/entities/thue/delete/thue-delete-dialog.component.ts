import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IThue } from '../thue.model';
import { ThueService } from '../service/thue.service';

@Component({
  templateUrl: './thue-delete-dialog.component.html',
})
export class ThueDeleteDialogComponent {
  thue?: IThue;

  constructor(protected thueService: ThueService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.thueService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
