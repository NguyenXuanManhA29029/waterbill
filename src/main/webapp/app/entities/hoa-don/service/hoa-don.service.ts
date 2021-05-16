import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHoaDon, getHoaDonIdentifier } from '../hoa-don.model';

export type EntityResponseType = HttpResponse<IHoaDon>;
export type EntityArrayResponseType = HttpResponse<IHoaDon[]>;

@Injectable({ providedIn: 'root' })
export class HoaDonService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/hoa-dons');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(hoaDon: IHoaDon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hoaDon);
    return this.http
      .post<IHoaDon>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hoaDon: IHoaDon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hoaDon);
    return this.http
      .put<IHoaDon>(`${this.resourceUrl}/${getHoaDonIdentifier(hoaDon) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hoaDon: IHoaDon): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hoaDon);
    return this.http
      .patch<IHoaDon>(`${this.resourceUrl}/${getHoaDonIdentifier(hoaDon) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHoaDon>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHoaDon[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHoaDonToCollectionIfMissing(hoaDonCollection: IHoaDon[], ...hoaDonsToCheck: (IHoaDon | null | undefined)[]): IHoaDon[] {
    const hoaDons: IHoaDon[] = hoaDonsToCheck.filter(isPresent);
    if (hoaDons.length > 0) {
      const hoaDonCollectionIdentifiers = hoaDonCollection.map(hoaDonItem => getHoaDonIdentifier(hoaDonItem)!);
      const hoaDonsToAdd = hoaDons.filter(hoaDonItem => {
        const hoaDonIdentifier = getHoaDonIdentifier(hoaDonItem);
        if (hoaDonIdentifier == null || hoaDonCollectionIdentifiers.includes(hoaDonIdentifier)) {
          return false;
        }
        hoaDonCollectionIdentifiers.push(hoaDonIdentifier);
        return true;
      });
      return [...hoaDonsToAdd, ...hoaDonCollection];
    }
    return hoaDonCollection;
  }

  protected convertDateFromClient(hoaDon: IHoaDon): IHoaDon {
    return Object.assign({}, hoaDon, {
      ngayThanhToan: hoaDon.ngayThanhToan?.isValid() ? hoaDon.ngayThanhToan.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.ngayThanhToan = res.body.ngayThanhToan ? dayjs(res.body.ngayThanhToan) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((hoaDon: IHoaDon) => {
        hoaDon.ngayThanhToan = hoaDon.ngayThanhToan ? dayjs(hoaDon.ngayThanhToan) : undefined;
      });
    }
    return res;
  }
}
