import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHoGiaDinh, HoGiaDinh } from '../ho-gia-dinh.model';
import { HoGiaDinhService } from '../service/ho-gia-dinh.service';
import { ITaiKhoan } from 'app/entities/tai-khoan/tai-khoan.model';
import { TaiKhoanService } from 'app/entities/tai-khoan/service/tai-khoan.service';

@Component({
  selector: 'jhi-ho-gia-dinh-update',
  templateUrl: './ho-gia-dinh-update.component.html',
})
export class HoGiaDinhUpdateComponent implements OnInit {
  isSaving = false;

  taikhoansCollection: ITaiKhoan[] = [];

  editForm = this.fb.group({
    id: [],
    tenChuHo: [],
    maHo: [],
    soCanCuoc: [],
    loaiHo: [],
    soHoNgheo: [],
    email: [],
    sdt: [],
    diaChi: [],
    taikhoan: [],
  });

  constructor(
    protected hoGiaDinhService: HoGiaDinhService,
    protected taiKhoanService: TaiKhoanService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoGiaDinh }) => {
      this.updateForm(hoGiaDinh);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hoGiaDinh = this.createFromForm();
    if (hoGiaDinh.id !== undefined) {
      this.subscribeToSaveResponse(this.hoGiaDinhService.update(hoGiaDinh));
    } else {
      this.subscribeToSaveResponse(this.hoGiaDinhService.create(hoGiaDinh));
    }
  }

  trackTaiKhoanById(index: number, item: ITaiKhoan): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoGiaDinh>>): void {
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

  protected updateForm(hoGiaDinh: IHoGiaDinh): void {
    this.editForm.patchValue({
      id: hoGiaDinh.id,
      tenChuHo: hoGiaDinh.tenChuHo,
      maHo: hoGiaDinh.maHo,
      soCanCuoc: hoGiaDinh.soCanCuoc,
      loaiHo: hoGiaDinh.loaiHo,
      soHoNgheo: hoGiaDinh.soHoNgheo,
      email: hoGiaDinh.email,
      sdt: hoGiaDinh.sdt,
      diaChi: hoGiaDinh.diaChi,
      taikhoan: hoGiaDinh.taikhoan,
    });

    this.taikhoansCollection = this.taiKhoanService.addTaiKhoanToCollectionIfMissing(this.taikhoansCollection, hoGiaDinh.taikhoan);
  }

  protected loadRelationshipsOptions(): void {
    this.taiKhoanService
      .query({ filter: 'hogiadinh-is-null' })
      .pipe(map((res: HttpResponse<ITaiKhoan[]>) => res.body ?? []))
      .pipe(
        map((taiKhoans: ITaiKhoan[]) =>
          this.taiKhoanService.addTaiKhoanToCollectionIfMissing(taiKhoans, this.editForm.get('taikhoan')!.value)
        )
      )
      .subscribe((taiKhoans: ITaiKhoan[]) => (this.taikhoansCollection = taiKhoans));
  }

  protected createFromForm(): IHoGiaDinh {
    return {
      ...new HoGiaDinh(),
      id: this.editForm.get(['id'])!.value,
      tenChuHo: this.editForm.get(['tenChuHo'])!.value,
      maHo: this.editForm.get(['maHo'])!.value,
      soCanCuoc: this.editForm.get(['soCanCuoc'])!.value,
      loaiHo: this.editForm.get(['loaiHo'])!.value,
      soHoNgheo: this.editForm.get(['soHoNgheo'])!.value,
      email: this.editForm.get(['email'])!.value,
      sdt: this.editForm.get(['sdt'])!.value,
      diaChi: this.editForm.get(['diaChi'])!.value,
      taikhoan: this.editForm.get(['taikhoan'])!.value,
    };
  }
}
