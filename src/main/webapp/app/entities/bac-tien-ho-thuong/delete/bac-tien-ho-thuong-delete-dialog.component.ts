import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBacTienHoThuong } from '../bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';

@Component({
  templateUrl: './bac-tien-ho-thuong-delete-dialog.component.html',
})
export class BacTienHoThuongDeleteDialogComponent {
  bacTienHoThuong?: IBacTienHoThuong;

  constructor(protected bacTienHoThuongService: BacTienHoThuongService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bacTienHoThuongService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
