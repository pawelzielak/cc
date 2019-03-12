import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICCG } from 'app/shared/model/ccg.model';

type EntityResponseType = HttpResponse<ICCG>;
type EntityArrayResponseType = HttpResponse<ICCG[]>;

@Injectable({ providedIn: 'root' })
export class CCGService {
    public resourceUrl = SERVER_API_URL + 'api/ccgs';

    constructor(protected http: HttpClient) {}

    create(cCG: ICCG): Observable<EntityResponseType> {
        return this.http.post<ICCG>(this.resourceUrl, cCG, { observe: 'response' });
    }

    update(cCG: ICCG): Observable<EntityResponseType> {
        return this.http.put<ICCG>(this.resourceUrl, cCG, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICCG>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICCG[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
