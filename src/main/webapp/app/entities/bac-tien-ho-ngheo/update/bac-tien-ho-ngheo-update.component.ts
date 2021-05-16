import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IBacTienHoNgheo, BacTienHoNgheo } from '../bac-tien-ho-ngheo.model';
import { BacTienHoNgheoService } from '../service/bac-tien-ho-ngheo.service';

@Component({
  selector: 'jhi-bac-tien-ho-ngheo-update',
  templateUrl: './bac-tien-ho-ngheo-update.component.html',
})
export class BacTienHoNgheoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenBac: [],
    giaTriBac: [],
  });

  constructor(
    protected bacTienHoNgheoService: BacTienHoNgheoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bacTienHoNgheo }) => {
      this.updateForm(bacTienHoNgheo);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bacTienHoNgheo = this.createFromForm();
    if (bacTienHoNgheo.id !== undefined) {
      this.subscribeToSaveResponse(this.bacTienHoNgheoService.update(bacTienHoNgheo));
    } else {
      this.subscribeToSaveResponse(this.bacTienHoNgheoService.create(bacTienHoNgheo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBacTienHoNgheo>>): void {
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

  protected updateForm(bacTienHoNgheo: IBacTienHoNgheo): void {
    this.editForm.patchValue({
      id: bacTienHoNgheo.id,
      tenBac: bacTienHoNgheo.tenBac,
      giaTriBac: bacTienHoNgheo.giaTriBac,
    });
  }

  protected createFromForm(): IBacTienHoNgheo {
    return {
      ...new BacTienHoNgheo(),
      id: this.editForm.get(['id'])!.value,
      tenBac: this.editForm.get(['tenBac'])!.value,
      giaTriBac: this.editForm.get(['giaTriBac'])!.value,
    };
  }
}
