import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaiKhoan } from '../tai-khoan.model';
import { TaiKhoanService } from '../service/tai-khoan.service';

@Component({
  templateUrl: './tai-khoan-delete-dialog.component.html',
})
export class TaiKhoanDeleteDialogComponent {
  taiKhoan?: ITaiKhoan;

  constructor(protected taiKhoanService: TaiKhoanService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taiKhoanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
