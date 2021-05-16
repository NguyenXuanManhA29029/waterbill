import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoGiaDinh } from '../ho-gia-dinh.model';

@Component({
  selector: 'jhi-ho-gia-dinh-detail',
  templateUrl: './ho-gia-dinh-detail.component.html',
})
export class HoGiaDinhDetailComponent implements OnInit {
  hoGiaDinh: IHoGiaDinh | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoGiaDinh }) => {
      this.hoGiaDinh = hoGiaDinh;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
