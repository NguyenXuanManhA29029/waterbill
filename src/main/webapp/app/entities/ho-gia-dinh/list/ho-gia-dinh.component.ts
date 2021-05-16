import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IHoGiaDinh } from '../ho-gia-dinh.model';
import { HoGiaDinhService } from '../service/ho-gia-dinh.service';
import { HoGiaDinhDeleteDialogComponent } from '../delete/ho-gia-dinh-delete-dialog.component';

@Component({
  selector: 'jhi-ho-gia-dinh',
  templateUrl: './ho-gia-dinh.component.html',
})
export class HoGiaDinhComponent implements OnInit {
  hoGiaDinhs?: IHoGiaDinh[];
  isLoading = false;

  constructor(protected hoGiaDinhService: HoGiaDinhService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.hoGiaDinhService.query().subscribe(
      (res: HttpResponse<IHoGiaDinh[]>) => {
        this.isLoading = false;
        this.hoGiaDinhs = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IHoGiaDinh): number {
    return item.id!;
  }

  delete(hoGiaDinh: IHoGiaDinh): void {
    const modalRef = this.modalService.open(HoGiaDinhDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.hoGiaDinh = hoGiaDinh;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
