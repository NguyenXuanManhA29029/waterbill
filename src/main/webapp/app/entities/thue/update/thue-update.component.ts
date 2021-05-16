import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IThue, Thue } from '../thue.model';
import { ThueService } from '../service/thue.service';

@Component({
  selector: 'jhi-thue-update',
  templateUrl: './thue-update.component.html',
})
export class ThueUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenThue: [],
    giaTriThue: [],
  });

  constructor(protected thueService: ThueService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ thue }) => {
      this.updateForm(thue);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const thue = this.createFromForm();
    if (thue.id !== undefined) {
      this.subscribeToSaveResponse(this.thueService.update(thue));
    } else {
      this.subscribeToSaveResponse(this.thueService.create(thue));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThue>>): void {
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

  protected updateForm(thue: IThue): void {
    this.editForm.patchValue({
      id: thue.id,
      tenThue: thue.tenThue,
      giaTriThue: thue.giaTriThue,
    });
  }

  protected createFromForm(): IThue {
    return {
      ...new Thue(),
      id: this.editForm.get(['id'])!.value,
      tenThue: this.editForm.get(['tenThue'])!.value,
      giaTriThue: this.editForm.get(['giaTriThue'])!.value,
    };
  }
}
