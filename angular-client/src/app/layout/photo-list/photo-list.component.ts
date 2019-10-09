import {Component, ViewChild, AfterViewInit, OnDestroy, OnInit} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {merge, of as observableOf, Subscription} from 'rxjs';
import {catchError, map, startWith, switchMap} from 'rxjs/operators';
import {PhotoService} from '../../../services/photo.service';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.css']
})
export class PhotoListComponent implements AfterViewInit, OnDestroy, OnInit {
  displayedColumns: string[] = ['id', 'created', 'filename', 'thumbnail', 'action'];
  data: PhotoInfoDto[] = [];
  subscription: Subscription;

  resultsLength = 0;
  isLoadingResults = true;

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  constructor(private photoService: PhotoService) {
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.subscription = this.photoService.messages$.subscribe((message) => {
      console.log(message);
      if (message.startsWith('SEARCH_FOR')) {
        const type = message.split(':')[1];
        if (type !== undefined && type !== '') {
          this.search(type);
        } else {
          this.refresh();
        }
      } else {
        this.refresh();
      }
    });
  }

  ngAfterViewInit() {
    this.refresh();
  }

  private search(value: string) {
    return this.photoService.searchPhotos(this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize, value).subscribe(data => {
      if (data !== null && data.content !== undefined && data.content.length > 0) {
        console.log(data)
        console.log(data.content.length)
        for (let i = 0; i < data.content.length; i++) {
          const base64 = data.content[i].thumbnail;
          data.content[i].thumbnail = 'data:image/png;base64,' + base64;
          console.log(data.content[i].thumbnail);
        }
        this.data = data.content;
      } else {
        alert('no photos found');
        this.data = [];
      }
    });
  }

  private refresh() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.photoService.getPhotos(this.sort.active, this.sort.direction, this.paginator.pageIndex, this.paginator.pageSize);
        }),
        map(data => {
          this.isLoadingResults = false;

          this.resultsLength = data.totalCount;
          console.log(data);

          return data.content;
        }),
        catchError(() => {
          console.log('asdad');
          this.isLoadingResults = false;
          return observableOf([]);
        })
      ).subscribe(data => {
      for (let i = 0; i < data.length; i++) {
        const base64 = data[i].thumbnail;
        data[i].thumbnail = 'data:image/png;base64,' + base64;
      }
      this.data = data;
    });
  }

  remove(id: any) {
    this.photoService.delete(id);
  }

  openPhoto(id: any) {
    this.photoService.getPhoto(id);
  }
}

export interface PhotoListDto {
  content: PhotoInfoDto[];
  totalCount: number;
}

export interface PhotoInfoDto {
  id: string;
  created: string;
  name: string;
  thumbnail: string;
}
