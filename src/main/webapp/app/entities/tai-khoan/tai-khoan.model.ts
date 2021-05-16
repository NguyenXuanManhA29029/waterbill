import { IHoGiaDinh } from 'app/entities/ho-gia-dinh/ho-gia-dinh.model';

export interface ITaiKhoan {
  id?: number;
  userName?: string | null;
  passWord?: string | null;
  hoGiaDinh?: IHoGiaDinh | null;
}

export class TaiKhoan implements ITaiKhoan {
  constructor(public id?: number, public userName?: string | null, public passWord?: string | null, public hoGiaDinh?: IHoGiaDinh | null) {}
}

export function getTaiKhoanIdentifier(taiKhoan: ITaiKhoan): number | undefined {
  return taiKhoan.id;
}
