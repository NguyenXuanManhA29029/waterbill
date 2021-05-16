import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaiKhoan } from '../tai-khoan.model';

@Component({
  selector: 'jhi-tai-khoan-detail',
  templateUrl: './tai-khoan-detail.component.html',
})
export class TaiKhoanDetailComponent implements OnInit {
  taiKhoan: ITaiKhoan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taiKhoan }) => {
      this.taiKhoan = taiKhoan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
