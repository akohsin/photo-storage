import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HeaderComponent} from './header/header.component';
import {PhotoListComponent} from './photo-list/photo-list.component';
import {PhotoComponent} from './photo-list/photo/photo.component';
import {LayoutRoutingModule} from './layout-routing.module';
import {LayoutComponent} from './layout.component';
import {
  MatButtonModule, MatCardModule,
  MatDialogModule,
  MatIconModule, MatInputModule,
  MatPaginatorModule, MatProgressSpinnerModule,
  MatSortModule,
  MatTableModule,
  MatToolbarModule
} from '@angular/material';
import {EmailDialogComponent} from '../dialogs/email-dialog-component';
import {PhotoDialogComponent} from '../dialogs/photo-dialog-component';
import {FormsModule} from '@angular/forms';
import {FileUploadModule} from 'ng2-file-upload';


@NgModule({
  declarations: [
    HeaderComponent,
    LayoutComponent,
    EmailDialogComponent,
    PhotoDialogComponent,
    PhotoListComponent,
    PhotoComponent],
  imports: [
    CommonModule,
    MatCardModule,
    MatDialogModule,
    FormsModule,
    FileUploadModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    LayoutRoutingModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule
  ],
  entryComponents: [
    EmailDialogComponent,
    PhotoDialogComponent
    ]
})
export class LayoutModule {
}
