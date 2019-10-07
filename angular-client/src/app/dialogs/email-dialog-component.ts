import {Component} from '@angular/core';
import {MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-email-dialog',
  templateUrl: 'email-dialog.component.html',
})
export class EmailDialogComponent {

  constructor(public dialogRef: MatDialogRef<EmailDialogComponent>) {
  }
}
