import {HttpClient} from '@angular/common/http';
import {Component, ViewChild, AfterViewInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, Observable, of as observableOf} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {PhotoService} from '../../../services/photo.service';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent  implements AfterViewInit {
  displayedColumns: string[] = ['id', 'created', 'name', 'thumbnail'];
  data: PhotoInfoDto[] = [];

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatSort, {static: false}) sort: MatSort;

  constructor(private photoService: PhotoService) {}

  ngAfterViewInit() {
    this.sort.sortChange.pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.photoService.getPhotos(this.sort.active, this.sort.direction);
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isRateLimitReached = false;

          this.resultsLength = data.totalCount;

          return data.items;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.data = data);
  }
}

export interface PhotoListDto {
  items: PhotoInfoDto[];
  totalCount: number;
}

export interface PhotoInfoDto {
  id: string;
  created: string;
  name: string;
  thumbnail: [];
}

/** An example database that the data source uses to retrieve data for the table. */
export class ExampleHttpDatabase {
  constructor(private httpClient: HttpClient) {}

  getRepoIssues(sort: string, order: string, page: number): Observable<PhotoListDto> {
    const href = 'https://api.github.com/search/issues';
    const requestUrl =
      `${href}?q=repo:angular/components&sort=${sort}&order=${order}&page=${page + 1}`;

    return this.httpClient.get<PhotoListDto>(requestUrl);
  }
}
