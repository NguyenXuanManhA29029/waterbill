import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoGiaDinh } from '../ho-gia-dinh.model';
import { HoGiaDinhService } from '../service/ho-gia-dinh.service';

@Component({
  templateUrl: './ho-gia-dinh-delete-dialog.component.html',
})
export class HoGiaDinhDeleteDialogComponent {
  hoGiaDinh?: IHoGiaDinh;

  constructor(protected hoGiaDinhService: HoGiaDinhService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hoGiaDinhService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
