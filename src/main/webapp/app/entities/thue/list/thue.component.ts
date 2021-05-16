import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IThue } from '../thue.model';
import { ThueService } from '../service/thue.service';
import { ThueDeleteDialogComponent } from '../delete/thue-delete-dialog.component';

@Component({
  selector: 'jhi-thue',
  templateUrl: './thue.component.html',
})
export class ThueComponent implements OnInit {
  thues?: IThue[];
  isLoading = false;

  constructor(protected thueService: ThueService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.thueService.query().subscribe(
      (res: HttpResponse<IThue[]>) => {
        this.isLoading = false;
        this.thues = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IThue): number {
    return item.id!;
  }

  delete(thue: IThue): void {
    const modalRef = this.modalService.open(ThueDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.thue = thue;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
