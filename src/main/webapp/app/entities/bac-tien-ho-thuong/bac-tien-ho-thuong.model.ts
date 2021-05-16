export interface IBacTienHoThuong {
  id?: number;
  tenBac?: string | null;
  giaTriBac?: number | null;
}

export class BacTienHoThuong implements IBacTienHoThuong {
  constructor(public id?: number, public tenBac?: string | null, public giaTriBac?: number | null) {}
}

export function getBacTienHoThuongIdentifier(bacTienHoThuong: IBacTienHoThuong): number | undefined {
  return bacTienHoThuong.id;
}
