import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from '@angular/material';
import {FileUploader} from 'ng2-file-upload';
import {NgForm} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-photo-dialog',
  templateUrl: 'photo-dialog.component.html',
  styleUrls: ['./photo-dialog.component.css']

})
export class PhotoDialogComponent implements OnInit{
  newFileName: any;
  public uploader: FileUploader;
  isLoadingResults: boolean;
  constructor(public dialogRef: MatDialogRef<PhotoDialogComponent>, private authService: AuthService) {
  }

  ngOnInit(): void {
    const token = localStorage.getItem('id_token');
    this.uploader = new FileUploader({
      url: `http://${environment.ip}:${environment.port}/photo/upload`,
      authToken: 'Bearer:' + token,
      disableMultipart: false,
    });
    this.uploader.onAfterAddingFile = (file) => file.withCredentials = true;

  }

  selectFile(file: any) {
    this.newFileName = file.name;
  }

  closeDialog() {

  }

  saveChanges() {
this.isLoadingResults = true;
  }
}
