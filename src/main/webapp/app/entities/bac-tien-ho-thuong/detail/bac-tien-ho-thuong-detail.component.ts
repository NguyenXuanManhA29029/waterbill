import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBacTienHoThuong } from '../bac-tien-ho-thuong.model';

@Component({
  selector: 'jhi-bac-tien-ho-thuong-detail',
  templateUrl: './bac-tien-ho-thuong-detail.component.html',
})
export class BacTienHoThuongDetailComponent implements OnInit {
  bacTienHoThuong: IBacTienHoThuong | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bacTienHoThuong }) => {
      this.bacTienHoThuong = bacTienHoThuong;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
