import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHoaDon, HoaDon } from '../hoa-don.model';
import { HoaDonService } from '../service/hoa-don.service';
import { IThue } from 'app/entities/thue/thue.model';
import { ThueService } from 'app/entities/thue/service/thue.service';
import { IBacTienHoNgheo } from 'app/entities/bac-tien-ho-ngheo/bac-tien-ho-ngheo.model';
import { BacTienHoNgheoService } from 'app/entities/bac-tien-ho-ngheo/service/bac-tien-ho-ngheo.service';
import { IBacTienHoThuong } from 'app/entities/bac-tien-ho-thuong/bac-tien-ho-thuong.model';
import { BacTienHoThuongService } from 'app/entities/bac-tien-ho-thuong/service/bac-tien-ho-thuong.service';
import { IHoGiaDinh } from 'app/entities/ho-gia-dinh/ho-gia-dinh.model';
import { HoGiaDinhService } from 'app/entities/ho-gia-dinh/service/ho-gia-dinh.service';

@Component({
  selector: 'jhi-hoa-don-update',
  templateUrl: './hoa-don-update.component.html',
})
export class HoaDonUpdateComponent implements OnInit {
  isSaving = false;

  thuesCollection: IThue[] = [];
  bacHoNgheosCollection: IBacTienHoNgheo[] = [];
  bacHoThuongsCollection: IBacTienHoThuong[] = [];
  giadinhsCollection: IHoGiaDinh[] = [];

  editForm = this.fb.group({
    id: [],
    tenChuHo: [],
    thangSuDung: [],
    chiSoMoi: [],
    chiSoCu: [],
    soNuoc: [],
    thanhTien: [],
    tienThue: [],
    tongTien: [],
    ngayThanhToan: [],
    trangThaiThanhToan: [],
    thue: [],
    bacHoNgheo: [],
    bacHoThuong: [],
    giadinh: [],
  });

  constructor(
    protected hoaDonService: HoaDonService,
    protected thueService: ThueService,
    protected bacTienHoNgheoService: BacTienHoNgheoService,
    protected bacTienHoThuongService: BacTienHoThuongService,
    protected hoGiaDinhService: HoGiaDinhService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoaDon }) => {
      if (hoaDon.id === undefined) {
        const today = dayjs().startOf('day');
        hoaDon.ngayThanhToan = today;
      }

      this.updateForm(hoaDon);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hoaDon = this.createFromForm();
    if (hoaDon.id !== undefined) {
      this.subscribeToSaveResponse(this.hoaDonService.update(hoaDon));
    } else {
      this.subscribeToSaveResponse(this.hoaDonService.create(hoaDon));
    }
  }

  trackThueById(index: number, item: IThue): number {
    return item.id!;
  }

  trackBacTienHoNgheoById(index: number, item: IBacTienHoNgheo): number {
    return item.id!;
  }

  trackBacTienHoThuongById(index: number, item: IBacTienHoThuong): number {
    return item.id!;
  }

  trackHoGiaDinhById(index: number, item: IHoGiaDinh): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoaDon>>): void {
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

  protected updateForm(hoaDon: IHoaDon): void {
    this.editForm.patchValue({
      id: hoaDon.id,
      tenChuHo: hoaDon.tenChuHo,
      thangSuDung: hoaDon.thangSuDung,
      chiSoMoi: hoaDon.chiSoMoi,
      chiSoCu: hoaDon.chiSoCu,
      soNuoc: hoaDon.soNuoc,
      thanhTien: hoaDon.thanhTien,
      tienThue: hoaDon.tienThue,
      tongTien: hoaDon.tongTien,
      ngayThanhToan: hoaDon.ngayThanhToan ? hoaDon.ngayThanhToan.format(DATE_TIME_FORMAT) : null,
      trangThaiThanhToan: hoaDon.trangThaiThanhToan,
      thue: hoaDon.thue,
      bacHoNgheo: hoaDon.bacHoNgheo,
      bacHoThuong: hoaDon.bacHoThuong,
      giadinh: hoaDon.giadinh,
    });

    this.thuesCollection = this.thueService.addThueToCollectionIfMissing(this.thuesCollection, hoaDon.thue);
    this.bacHoNgheosCollection = this.bacTienHoNgheoService.addBacTienHoNgheoToCollectionIfMissing(
      this.bacHoNgheosCollection,
      hoaDon.bacHoNgheo
    );
    this.bacHoThuongsCollection = this.bacTienHoThuongService.addBacTienHoThuongToCollectionIfMissing(
      this.bacHoThuongsCollection,
      hoaDon.bacHoThuong
    );
    this.giadinhsCollection = this.hoGiaDinhService.addHoGiaDinhToCollectionIfMissing(this.giadinhsCollection, hoaDon.giadinh);
  }

  protected loadRelationshipsOptions(): void {
    this.thueService
      .query({ filter: 'hoadon-is-null' })
      .pipe(map((res: HttpResponse<IThue[]>) => res.body ?? []))
      .pipe(map((thues: IThue[]) => this.thueService.addThueToCollectionIfMissing(thues, this.editForm.get('thue')!.value)))
      .subscribe((thues: IThue[]) => (this.thuesCollection = thues));

    this.bacTienHoNgheoService
      .query({ filter: 'hoadon-is-null' })
      .pipe(map((res: HttpResponse<IBacTienHoNgheo[]>) => res.body ?? []))
      .pipe(
        map((bacTienHoNgheos: IBacTienHoNgheo[]) =>
          this.bacTienHoNgheoService.addBacTienHoNgheoToCollectionIfMissing(bacTienHoNgheos, this.editForm.get('bacHoNgheo')!.value)
        )
      )
      .subscribe((bacTienHoNgheos: IBacTienHoNgheo[]) => (this.bacHoNgheosCollection = bacTienHoNgheos));

    this.bacTienHoThuongService
      .query({ filter: 'hoadon-is-null' })
      .pipe(map((res: HttpResponse<IBacTienHoThuong[]>) => res.body ?? []))
      .pipe(
        map((bacTienHoThuongs: IBacTienHoThuong[]) =>
          this.bacTienHoThuongService.addBacTienHoThuongToCollectionIfMissing(bacTienHoThuongs, this.editForm.get('bacHoThuong')!.value)
        )
      )
      .subscribe((bacTienHoThuongs: IBacTienHoThuong[]) => (this.bacHoThuongsCollection = bacTienHoThuongs));

    this.hoGiaDinhService
      .query({ filter: 'hoadongd-is-null' })
      .pipe(map((res: HttpResponse<IHoGiaDinh[]>) => res.body ?? []))
      .pipe(
        map((hoGiaDinhs: IHoGiaDinh[]) =>
          this.hoGiaDinhService.addHoGiaDinhToCollectionIfMissing(hoGiaDinhs, this.editForm.get('giadinh')!.value)
        )
      )
      .subscribe((hoGiaDinhs: IHoGiaDinh[]) => (this.giadinhsCollection = hoGiaDinhs));
  }

  protected createFromForm(): IHoaDon {
    return {
      ...new HoaDon(),
      id: this.editForm.get(['id'])!.value,
      tenChuHo: this.editForm.get(['tenChuHo'])!.value,
      thangSuDung: this.editForm.get(['thangSuDung'])!.value,
      chiSoMoi: this.editForm.get(['chiSoMoi'])!.value,
      chiSoCu: this.editForm.get(['chiSoCu'])!.value,
      soNuoc: this.editForm.get(['soNuoc'])!.value,
      thanhTien: this.editForm.get(['thanhTien'])!.value,
      tienThue: this.editForm.get(['tienThue'])!.value,
      tongTien: this.editForm.get(['tongTien'])!.value,
      ngayThanhToan: this.editForm.get(['ngayThanhToan'])!.value
        ? dayjs(this.editForm.get(['ngayThanhToan'])!.value, DATE_TIME_FORMAT)
        : undefined,
      trangThaiThanhToan: this.editForm.get(['trangThaiThanhToan'])!.value,
      thue: this.editForm.get(['thue'])!.value,
      bacHoNgheo: this.editForm.get(['bacHoNgheo'])!.value,
      bacHoThuong: this.editForm.get(['bacHoThuong'])!.value,
      giadinh: this.editForm.get(['giadinh'])!.value,
    };
  }
}
