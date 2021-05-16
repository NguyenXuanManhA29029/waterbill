import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaiKhoan } from '../tai-khoan.model';
import { TaiKhoanService } from '../service/tai-khoan.service';
import { TaiKhoanDeleteDialogComponent } from '../delete/tai-khoan-delete-dialog.component';

@Component({
  selector: 'jhi-tai-khoan',
  templateUrl: './tai-khoan.component.html',
})
export class TaiKhoanComponent implements OnInit {
  taiKhoans?: ITaiKhoan[];
  isLoading = false;

  constructor(protected taiKhoanService: TaiKhoanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taiKhoanService.query().subscribe(
      (res: HttpResponse<ITaiKhoan[]>) => {
        this.isLoading = false;
        this.taiKhoans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaiKhoan): number {
    return item.id!;
  }

  delete(taiKhoan: ITaiKhoan): void {
    const modalRef = this.modalService.open(TaiKhoanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taiKhoan = taiKhoan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
