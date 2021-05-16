export interface IThue {
  id?: number;
  tenThue?: string | null;
  giaTriThue?: number | null;
}

export class Thue implements IThue {
  constructor(public id?: number, public tenThue?: string | null, public giaTriThue?: number | null) {}
}

export function getThueIdentifier(thue: IThue): number | undefined {
  return thue.id;
}
