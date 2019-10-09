import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-display-dialog',
  templateUrl: 'photo-display.component.html',
  styleUrls: ['./photo-display.component.css']

})
export class PhotoDisplayComponent implements OnInit {
  imageInBase64: any;

  constructor(public dialogRef: MatDialogRef<PhotoDisplayComponent>, @Inject(MAT_DIALOG_DATA) public data: { base64: string }) {
    this.imageInBase64 = 'data:image/png;base64,' + this.data.base64;

  }

  ngOnInit(): void {
  }

  closeDialog() {
    this.dialogRef.close();
  }
}
