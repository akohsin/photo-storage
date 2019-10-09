import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {PhotoInfoDto, PhotoListDto} from '../app/layout/photo-list/photo-list.component';
import {HttpClient, HttpParams, HttpResponse} from '@angular/common/http';
import {environment} from '../environments/environment';
import {MatDialog} from '@angular/material';
import {PhotoDisplayComponent} from '../app/dialogs/photo-display-dialog/photo-display-component';

@Injectable({
  providedIn: 'root'
})
export class PhotoService {

  private topic: Subject<string>;
  messages$: Observable<string>;

  constructor(private httpClient: HttpClient, private matDialog: MatDialog) {
    this.topic = new Subject<string>();
    this.messages$ = this.topic.asObservable();
  }

  share(message: string) {
    this.topic.next(message);
  }

  getPhotos(sort: string, order: string, page: number): Observable<PhotoListDto> {
    const requestUrl = `http://${environment.ip}:${environment.port}/photo`;
    const params = new HttpParams().set('perPage', '30').set('page', page.toFixed(0)).set('sortBy', sort).set('direction', order);
    return this.httpClient.get<PhotoListDto>(requestUrl, {params, observe: 'body'});
  }

  delete(id: any) {
    const requestUrl = `http://${environment.ip}:${environment.port}/photo/${id}`;
    return this.httpClient.delete<PhotoListDto>(requestUrl, {observe: 'body'}).subscribe(() => {
      alert('Deleted successfully');
      this.share('UPDATE');
    });

  }

  getPhoto(id: any) {
    const requestUrl = `http://${environment.ip}:${environment.port}/photo/${id}`;
    this.httpClient.get<PhotoInfoDto>(requestUrl, {observe: 'body'}).subscribe((data) => {
      this.matDialog.open(PhotoDisplayComponent, {data: {base64: data.thumbnail}});
    });
  }

  searchPhotos(sort: string, order: string, page: number, value: string): Observable<PhotoListDto> {
    const requestUrl = `http://${environment.ip}:${environment.port}/photo`;
    const params = new HttpParams().set('perPage', '30').set('page', page.toFixed(0)).set('sortBy', sort).set('direction', order)
      .set('filename', value);
    return this.httpClient.get<PhotoListDto>(requestUrl, {params, observe: 'body'});
  }
}

// "perPage") Integer perPage,
//   @RequestParam(name = "page") Integer page,
//   @RequestParam(name = "sortBy") SortField sortField,
//   @RequestParam(name = "filename", required = false) String fileName,
//   @RequestParam(name = "direction"
