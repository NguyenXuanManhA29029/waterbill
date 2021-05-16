import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBacTienHoNgheo } from '../bac-tien-ho-ngheo.model';

@Component({
  selector: 'jhi-bac-tien-ho-ngheo-detail',
  templateUrl: './bac-tien-ho-ngheo-detail.component.html',
})
export class BacTienHoNgheoDetailComponent implements OnInit {
  bacTienHoNgheo: IBacTienHoNgheo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bacTienHoNgheo }) => {
      this.bacTienHoNgheo = bacTienHoNgheo;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
