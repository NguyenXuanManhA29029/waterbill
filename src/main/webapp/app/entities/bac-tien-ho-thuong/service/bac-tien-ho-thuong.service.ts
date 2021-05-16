import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBacTienHoThuong, getBacTienHoThuongIdentifier } from '../bac-tien-ho-thuong.model';

export type EntityResponseType = HttpResponse<IBacTienHoThuong>;
export type EntityArrayResponseType = HttpResponse<IBacTienHoThuong[]>;

@Injectable({ providedIn: 'root' })
export class BacTienHoThuongService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/bac-tien-ho-thuongs');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(bacTienHoThuong: IBacTienHoThuong): Observable<EntityResponseType> {
    return this.http.post<IBacTienHoThuong>(this.resourceUrl, bacTienHoThuong, { observe: 'response' });
  }

  update(bacTienHoThuong: IBacTienHoThuong): Observable<EntityResponseType> {
    return this.http.put<IBacTienHoThuong>(
      `${this.resourceUrl}/${getBacTienHoThuongIdentifier(bacTienHoThuong) as number}`,
      bacTienHoThuong,
      { observe: 'response' }
    );
  }

  partialUpdate(bacTienHoThuong: IBacTienHoThuong): Observable<EntityResponseType> {
    return this.http.patch<IBacTienHoThuong>(
      `${this.resourceUrl}/${getBacTienHoThuongIdentifier(bacTienHoThuong) as number}`,
      bacTienHoThuong,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBacTienHoThuong>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBacTienHoThuong[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBacTienHoThuongToCollectionIfMissing(
    bacTienHoThuongCollection: IBacTienHoThuong[],
    ...bacTienHoThuongsToCheck: (IBacTienHoThuong | null | undefined)[]
  ): IBacTienHoThuong[] {
    const bacTienHoThuongs: IBacTienHoThuong[] = bacTienHoThuongsToCheck.filter(isPresent);
    if (bacTienHoThuongs.length > 0) {
      const bacTienHoThuongCollectionIdentifiers = bacTienHoThuongCollection.map(
        bacTienHoThuongItem => getBacTienHoThuongIdentifier(bacTienHoThuongItem)!
      );
      const bacTienHoThuongsToAdd = bacTienHoThuongs.filter(bacTienHoThuongItem => {
        const bacTienHoThuongIdentifier = getBacTienHoThuongIdentifier(bacTienHoThuongItem);
        if (bacTienHoThuongIdentifier == null || bacTienHoThuongCollectionIdentifiers.includes(bacTienHoThuongIdentifier)) {
          return false;
        }
        bacTienHoThuongCollectionIdentifiers.push(bacTienHoThuongIdentifier);
        return true;
      });
      return [...bacTienHoThuongsToAdd, ...bacTienHoThuongCollection];
    }
    return bacTienHoThuongCollection;
  }
}
