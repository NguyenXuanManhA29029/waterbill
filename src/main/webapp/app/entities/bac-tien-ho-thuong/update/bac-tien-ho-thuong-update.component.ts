import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBacTienHoThuong, BacTienHoThuong } from '../bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from '../service/bac-tien-ho-thuong.service';

@Component({
  selector: 'jhi-bac-tien-ho-thuong-update',
  templateUrl: './bac-tien-ho-thuong-update.component.html',
})
export class BacTienHoThuongUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenBac: [],
    giaTriBac: [],
  });

  constructor(
    protected bacTienHoThuongService: BacTienHoThuongService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bacTienHoThuong }) => {
      this.updateForm(bacTienHoThuong);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bacTienHoThuong = this.createFromForm();
    if (bacTienHoThuong.id !== undefined) {
      this.subscribeToSaveResponse(this.bacTienHoThuongService.update(bacTienHoThuong));
    } else {
      this.subscribeToSaveResponse(this.bacTienHoThuongService.create(bacTienHoThuong));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBacTienHoThuong>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(bacTienHoThuong: IBacTienHoThuong): void {
    this.editForm.patchValue({
      id: bacTienHoThuong.id,
      tenBac: bacTienHoThuong.tenBac,
      giaTriBac: bacTienHoThuong.giaTriBac,
    });
  }

  protected createFromForm(): IBacTienHoThuong {
    return {
      ...new BacTienHoThuong(),
      id: this.editForm.get(['id'])!.value,
      tenBac: this.editForm.get(['tenBac'])!.value,
      giaTriBac: this.editForm.get(['giaTriBac'])!.value,
    };
  }
}
