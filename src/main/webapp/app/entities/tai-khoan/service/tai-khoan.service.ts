import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaiKhoan, getTaiKhoanIdentifier } from '../tai-khoan.model';

export type EntityResponseType = HttpResponse<ITaiKhoan>;
export type EntityArrayResponseType = HttpResponse<ITaiKhoan[]>;

@Injectable({ providedIn: 'root' })
export class TaiKhoanService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/tai-khoans');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taiKhoan: ITaiKhoan): Observable<EntityResponseType> {
    return this.http.post<ITaiKhoan>(this.resourceUrl, taiKhoan, { observe: 'response' });
  }

  update(taiKhoan: ITaiKhoan): Observable<EntityResponseType> {
    return this.http.put<ITaiKhoan>(`${this.resourceUrl}/${getTaiKhoanIdentifier(taiKhoan) as number}`, taiKhoan, { observe: 'response' });
  }

  partialUpdate(taiKhoan: ITaiKhoan): Observable<EntityResponseType> {
    return this.http.patch<ITaiKhoan>(`${this.resourceUrl}/${getTaiKhoanIdentifier(taiKhoan) as number}`, taiKhoan, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITaiKhoan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITaiKhoan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaiKhoanToCollectionIfMissing(taiKhoanCollection: ITaiKhoan[], ...taiKhoansToCheck: (ITaiKhoan | null | undefined)[]): ITaiKhoan[] {
    const taiKhoans: ITaiKhoan[] = taiKhoansToCheck.filter(isPresent);
    if (taiKhoans.length > 0) {
      const taiKhoanCollectionIdentifiers = taiKhoanCollection.map(taiKhoanItem => getTaiKhoanIdentifier(taiKhoanItem)!);
      const taiKhoansToAdd = taiKhoans.filter(taiKhoanItem => {
        const taiKhoanIdentifier = getTaiKhoanIdentifier(taiKhoanItem);
        if (taiKhoanIdentifier == null || taiKhoanCollectionIdentifiers.includes(taiKhoanIdentifier)) {
          return false;
        }
        taiKhoanCollectionIdentifiers.push(taiKhoanIdentifier);
        return true;
      });
      return [...taiKhoansToAdd, ...taiKhoanCollection];
    }
    return taiKhoanCollection;
  }
}
