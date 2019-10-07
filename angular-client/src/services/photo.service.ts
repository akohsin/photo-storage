import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {PhotoListDto} from '../app/layout/photo-list/photo-list.component';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  constructor(private httpClient: HttpClient) { }

  getPhotos(active: string, direction: string) {
    console.log(active)
    console.log(direction)
    return this.getRepoIssues(active, direction,1);
  }

  getRepoIssues(sort: string, order: string, page: number): Observable<PhotoListDto> {
    const href = 'https://api.github.com/search/issues';
    const requestUrl =
      `${href}?q=repo:angular/components&sort=${sort}&order=${order}&page=1`;

    return this.httpClient.get<PhotoListDto>(requestUrl);
  }

}
