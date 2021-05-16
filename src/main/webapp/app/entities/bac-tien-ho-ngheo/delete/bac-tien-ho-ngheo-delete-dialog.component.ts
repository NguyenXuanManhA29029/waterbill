import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBacTienHoNgheo } from '../bac-tien-ho-ngheo.model';
import { BacTienHoNgheoService } from '../service/bac-tien-ho-ngheo.service';

@Component({
  templateUrl: './bac-tien-ho-ngheo-delete-dialog.component.html',
})
export class BacTienHoNgheoDeleteDialogComponent {
  bacTienHoNgheo?: IBacTienHoNgheo;

  constructor(protected bacTienHoNgheoService: BacTienHoNgheoService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bacTienHoNgheoService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
