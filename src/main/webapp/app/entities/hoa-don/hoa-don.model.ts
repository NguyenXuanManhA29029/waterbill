import * as dayjs from 'dayjs';
import { IThue } from 'app/entities/thue/thue.model';
import { IBacTienHoNgheo } from 'app/entities/bac-tien-ho-ngheo/bac-tien-ho-ngheo.model';
import { IBacTienHoThuong } from 'app/entities/bac-tien-ho-thuong/bac-tien-ho-thuong.model';
import { IHoGiaDinh } from 'app/entities/ho-gia-dinh/ho-gia-dinh.model';

export interface IHoaDon {
  id?: number;
  tenChuHo?: string | null;
  thangSuDung?: number | null;
  chiSoMoi?: number | null;
  chiSoCu?: number | null;
  soNuoc?: number | null;
  thanhTien?: number | null;
  tienThue?: number | null;
  tongTien?: number | null;
  ngayThanhToan?: dayjs.Dayjs | null;
  trangThaiThanhToan?: string | null;
  thue?: IThue | null;
  bacHoNgheo?: IBacTienHoNgheo | null;
  bacHoThuong?: IBacTienHoThuong | null;
  giadinh?: IHoGiaDinh | null;
}

export class HoaDon implements IHoaDon {
  constructor(
    public id?: number,
    public tenChuHo?: string | null,
    public thangSuDung?: number | null,
    public chiSoMoi?: number | null,
    public chiSoCu?: number | null,
    public soNuoc?: number | null,
    public thanhTien?: number | null,
    public tienThue?: number | null,
    public tongTien?: number | null,
    public ngayThanhToan?: dayjs.Dayjs | null,
    public trangThaiThanhToan?: string | null,
    public thue?: IThue | null,
    public bacHoNgheo?: IBacTienHoNgheo | null,
    public bacHoThuong?: IBacTienHoThuong | null,
    public giadinh?: IHoGiaDinh | null
  ) {}
}

export function getHoaDonIdentifier(hoaDon: IHoaDon): number | undefined {
  return hoaDon.id;
}
