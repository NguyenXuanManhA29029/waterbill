import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBacTienHoNgheo } from '../bac-tien-ho-ngheo.model';
import { BacTienHoNgheoService } from '../service/bac-tien-ho-ngheo.service';
import { BacTienHoNgheoDeleteDialogComponent } from '../delete/bac-tien-ho-ngheo-delete-dialog.component';

@Component({
  selector: 'jhi-bac-tien-ho-ngheo',
  templateUrl: './bac-tien-ho-ngheo.component.html',
})
export class BacTienHoNgheoComponent implements OnInit {
  bacTienHoNgheos?: IBacTienHoNgheo[];
  isLoading = false;

  constructor(protected bacTienHoNgheoService: BacTienHoNgheoService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.bacTienHoNgheoService.query().subscribe(
      (res: HttpResponse<IBacTienHoNgheo[]>) => {
        this.isLoading = false;
        this.bacTienHoNgheos = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBacTienHoNgheo): number {
    return item.id!;
  }

  delete(bacTienHoNgheo: IBacTienHoNgheo): void {
    const modalRef = this.modalService.open(BacTienHoNgheoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bacTienHoNgheo = bacTienHoNgheo;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
