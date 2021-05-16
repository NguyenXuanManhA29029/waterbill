import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHoGiaDinh, getHoGiaDinhIdentifier } from '../ho-gia-dinh.model';

export type EntityResponseType = HttpResponse<IHoGiaDinh>;
export type EntityArrayResponseType = HttpResponse<IHoGiaDinh[]>;

@Injectable({ providedIn: 'root' })
export class HoGiaDinhService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ho-gia-dinhs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(hoGiaDinh: IHoGiaDinh): Observable<EntityResponseType> {
    return this.http.post<IHoGiaDinh>(this.resourceUrl, hoGiaDinh, { observe: 'response' });
  }

  update(hoGiaDinh: IHoGiaDinh): Observable<EntityResponseType> {
    return this.http.put<IHoGiaDinh>(`${this.resourceUrl}/${getHoGiaDinhIdentifier(hoGiaDinh) as number}`, hoGiaDinh, {
      observe: 'response',
    });
  }

  partialUpdate(hoGiaDinh: IHoGiaDinh): Observable<EntityResponseType> {
    return this.http.patch<IHoGiaDinh>(`${this.resourceUrl}/${getHoGiaDinhIdentifier(hoGiaDinh) as number}`, hoGiaDinh, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHoGiaDinh>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHoGiaDinh[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHoGiaDinhToCollectionIfMissing(
    hoGiaDinhCollection: IHoGiaDinh[],
    ...hoGiaDinhsToCheck: (IHoGiaDinh | null | undefined)[]
  ): IHoGiaDinh[] {
    const hoGiaDinhs: IHoGiaDinh[] = hoGiaDinhsToCheck.filter(isPresent);
    if (hoGiaDinhs.length > 0) {
      const hoGiaDinhCollectionIdentifiers = hoGiaDinhCollection.map(hoGiaDinhItem => getHoGiaDinhIdentifier(hoGiaDinhItem)!);
      const hoGiaDinhsToAdd = hoGiaDinhs.filter(hoGiaDinhItem => {
        const hoGiaDinhIdentifier = getHoGiaDinhIdentifier(hoGiaDinhItem);
        if (hoGiaDinhIdentifier == null || hoGiaDinhCollectionIdentifiers.includes(hoGiaDinhIdentifier)) {
          return false;
        }
        hoGiaDinhCollectionIdentifiers.push(hoGiaDinhIdentifier);
        return true;
      });
      return [...hoGiaDinhsToAdd, ...hoGiaDinhCollection];
    }
    return hoGiaDinhCollection;
  }
}
