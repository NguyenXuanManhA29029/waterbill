import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBacTienHoNgheo, getBacTienHoNgheoIdentifier } from '../bac-tien-ho-ngheo.model';

export type EntityResponseType = HttpResponse<IBacTienHoNgheo>;
export type EntityArrayResponseType = HttpResponse<IBacTienHoNgheo[]>;

@Injectable({ providedIn: 'root' })
export class BacTienHoNgheoService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/bac-tien-ho-ngheos');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(bacTienHoNgheo: IBacTienHoNgheo): Observable<EntityResponseType> {
    return this.http.post<IBacTienHoNgheo>(this.resourceUrl, bacTienHoNgheo, { observe: 'response' });
  }

  update(bacTienHoNgheo: IBacTienHoNgheo): Observable<EntityResponseType> {
    return this.http.put<IBacTienHoNgheo>(`${this.resourceUrl}/${getBacTienHoNgheoIdentifier(bacTienHoNgheo) as number}`, bacTienHoNgheo, {
      observe: 'response',
    });
  }

  partialUpdate(bacTienHoNgheo: IBacTienHoNgheo): Observable<EntityResponseType> {
    return this.http.patch<IBacTienHoNgheo>(
      `${this.resourceUrl}/${getBacTienHoNgheoIdentifier(bacTienHoNgheo) as number}`,
      bacTienHoNgheo,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBacTienHoNgheo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBacTienHoNgheo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBacTienHoNgheoToCollectionIfMissing(
    bacTienHoNgheoCollection: IBacTienHoNgheo[],
    ...bacTienHoNgheosToCheck: (IBacTienHoNgheo | null | undefined)[]
  ): IBacTienHoNgheo[] {
    const bacTienHoNgheos: IBacTienHoNgheo[] = bacTienHoNgheosToCheck.filter(isPresent);
    if (bacTienHoNgheos.length > 0) {
      const bacTienHoNgheoCollectionIdentifiers = bacTienHoNgheoCollection.map(
        bacTienHoNgheoItem => getBacTienHoNgheoIdentifier(bacTienHoNgheoItem)!
      );
      const bacTienHoNgheosToAdd = bacTienHoNgheos.filter(bacTienHoNgheoItem => {
        const bacTienHoNgheoIdentifier = getBacTienHoNgheoIdentifier(bacTienHoNgheoItem);
        if (bacTienHoNgheoIdentifier == null || bacTienHoNgheoCollectionIdentifiers.includes(bacTienHoNgheoIdentifier)) {
          return false;
        }
        bacTienHoNgheoCollectionIdentifiers.push(bacTienHoNgheoIdentifier);
        return true;
      });
      return [...bacTienHoNgheosToAdd, ...bacTienHoNgheoCollection];
    }
    return bacTienHoNgheoCollection;
  }
}
