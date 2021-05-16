import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBacTienHoThuong } from '../bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';
import { BacTienHoThuongDeleteDialogComponent } from '../delete/bac-tien-ho-thuong-delete-dialog.component';

@Component({
  selector: 'jhi-bac-tien-ho-thuong',
  templateUrl: './bac-tien-ho-thuong.component.html',
})
export class BacTienHoThuongComponent implements OnInit {
  bacTienHoThuongs?: IBacTienHoThuong[];
  isLoading = false;

  constructor(protected bacTienHoThuongService: BacTienHoThuongService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bacTienHoThuongService.query().subscribe(
      (res: HttpResponse<IBacTienHoThuong[]>) => {
        this.isLoading = false;
        this.bacTienHoThuongs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBacTienHoThuong): number {
    return item.id!;
  }

  delete(bacTienHoThuong: IBacTienHoThuong): void {
    const modalRef = this.modalService.open(BacTienHoThuongDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bacTienHoThuong = bacTienHoThuong;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
