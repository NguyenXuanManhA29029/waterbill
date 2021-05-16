import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITaiKhoan, TaiKhoan } from '../tai-khoan.model';
import { TaiKhoanService } from '../service/tai-khoan.service';

@Component({
  selector: 'jhi-tai-khoan-update',
  templateUrl: './tai-khoan-update.component.html',
})
export class TaiKhoanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userName: [],
    passWord: [],
  });

  constructor(protected taiKhoanService: TaiKhoanService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taiKhoan }) => {
      this.updateForm(taiKhoan);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taiKhoan = this.createFromForm();
    if (taiKhoan.id !== undefined) {
      this.subscribeToSaveResponse(this.taiKhoanService.update(taiKhoan));
    } else {
      this.subscribeToSaveResponse(this.taiKhoanService.create(taiKhoan));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaiKhoan>>): void {
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

  protected updateForm(taiKhoan: ITaiKhoan): void {
    this.editForm.patchValue({
      id: taiKhoan.id,
      userName: taiKhoan.userName,
      passWord: taiKhoan.passWord,
    });
  }

  protected createFromForm(): ITaiKhoan {
    return {
      ...new TaiKhoan(),
      id: this.editForm.get(['id'])!.value,
      userName: this.editForm.get(['userName'])!.value,
      passWord: this.editForm.get(['passWord'])!.value,
    };
  }
}
