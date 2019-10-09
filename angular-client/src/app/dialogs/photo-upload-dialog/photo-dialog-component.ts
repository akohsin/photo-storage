import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {FileUploader} from 'ng2-file-upload';
import {NgForm} from '@angular/forms';
import {AuthService} from '../../../services/auth.service';
import {environment} from '../../../environments/environment';
import {PhotoService} from '../../../services/photo.service';

@Component({
  selector: 'app-photo-dialog',
  templateUrl: 'photo-dialog.component.html',
  styleUrls: ['./photo-dialog.component.css']

})
export class PhotoDialogComponent implements OnInit {
  newFileName: any;
  public uploader: FileUploader;
  isLoadingResults: boolean;

  constructor(private matDialog: MatDialog, public dialogRef: MatDialogRef<PhotoDialogComponent>, private authService: AuthService,
              private photoService: PhotoService) {
  }

  ngOnInit(): void {
    this.uploader = new FileUploader({
      url: `http://${environment.ip}:${environment.port}/photo/`,
      authToken: this.authService.token,
      disableMultipart: false,
    });
    this.uploader.onAfterAddingFile = (file) => file.withCredentials = true;

  }

  selectFile(file: any) {
    this.newFileName = file.name;
  }

  closeDialog() {
    this.matDialog.closeAll();
  }

  saveChanges() {
    this.uploader.uploadAll();
    this.uploader.onCompleteItem = ((item: any, response: any, status: any, headers: any) => {
      alert('Success');
      this.photoService.share('newPhoto');
    });
    this.matDialog.closeAll();
  }
}
