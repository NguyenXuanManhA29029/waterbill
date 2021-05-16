export interface IBacTienHoNgheo {
  id?: number;
  tenBac?: string | null;
  giaTriBac?: number | null;
}

export class BacTienHoNgheo implements IBacTienHoNgheo {
  constructor(public id?: number, public tenBac?: string | null, public giaTriBac?: number | null) {}
}

export function getBacTienHoNgheoIdentifier(bacTienHoNgheo: IBacTienHoNgheo): number | undefined {
  return bacTienHoNgheo.id;
}
