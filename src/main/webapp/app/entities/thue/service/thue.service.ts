import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IThue, getThueIdentifier } from '../thue.model';

export type EntityResponseType = HttpResponse<IThue>;
export type EntityArrayResponseType = HttpResponse<IThue[]>;

@Injectable({ providedIn: 'root' })
export class ThueService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/thues');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(thue: IThue): Observable<EntityResponseType> {
    return this.http.post<IThue>(this.resourceUrl, thue, { observe: 'response' });
  }

  update(thue: IThue): Observable<EntityResponseType> {
    return this.http.put<IThue>(`${this.resourceUrl}/${getThueIdentifier(thue) as number}`, thue, { observe: 'response' });
  }

  partialUpdate(thue: IThue): Observable<EntityResponseType> {
    return this.http.patch<IThue>(`${this.resourceUrl}/${getThueIdentifier(thue) as number}`, thue, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IThue>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IThue[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addThueToCollectionIfMissing(thueCollection: IThue[], ...thuesToCheck: (IThue | null | undefined)[]): IThue[] {
    const thues: IThue[] = thuesToCheck.filter(isPresent);
    if (thues.length > 0) {
      const thueCollectionIdentifiers = thueCollection.map(thueItem => getThueIdentifier(thueItem)!);
      const thuesToAdd = thues.filter(thueItem => {
        const thueIdentifier = getThueIdentifier(thueItem);
        if (thueIdentifier == null || thueCollectionIdentifiers.includes(thueIdentifier)) {
          return false;
        }
        thueCollectionIdentifiers.push(thueIdentifier);
        return true;
      });
      return [...thuesToAdd, ...thueCollection];
    }
    return thueCollection;
  }
}
