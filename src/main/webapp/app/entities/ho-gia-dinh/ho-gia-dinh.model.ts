import { ITaiKhoan } from 'app/entities/tai-khoan/tai-khoan.model';
import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';

export interface IHoGiaDinh {
  id?: number;
  tenChuHo?: string | null;
  maHo?: string | null;
  soCanCuoc?: string | null;
  loaiHo?: string | null;
  soHoNgheo?: string | null;
  email?: string | null;
  sdt?: string | null;
  diaChi?: string | null;
  taikhoan?: ITaiKhoan | null;
  hoadongd?: IHoaDon | null;
}

export class HoGiaDinh implements IHoGiaDinh {
  constructor(
    public id?: number,
    public tenChuHo?: string | null,
    public maHo?: string | null,
    public soCanCuoc?: string | null,
    public loaiHo?: string | null,
    public soHoNgheo?: string | null,
    public email?: string | null,
    public sdt?: string | null,
    public diaChi?: string | null,
    public taikhoan?: ITaiKhoan | null,
    public hoadongd?: IHoaDon | null
  ) {}
}

export function getHoGiaDinhIdentifier(hoGiaDinh: IHoGiaDinh): number | undefined {
  return hoGiaDinh.id;
}
