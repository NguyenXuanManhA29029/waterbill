import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThue } from '../thue.model';

@Component({
  selector: 'jhi-thue-detail',
  templateUrl: './thue-detail.component.html',
})
export class ThueDetailComponent implements OnInit {
  thue: IThue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thue }) => {
      this.thue = thue;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
