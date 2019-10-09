import {Component} from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {FormControl, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-email-dialog',
  templateUrl: 'email-dialog.component.html',
  styleUrls: ['./email-dialog.component.css']
})
export class EmailDialogComponent {
  emailFormControl = new FormControl('', [
    Validators.required,
    this.userService.emailValidator,
  ]);

  constructor(private matDialog: MatDialog, private router: Router, private userService: UserService,
              public dialogRef: MatDialogRef<EmailDialogComponent>) {
  }

  valid() {
    return this.emailFormControl.valid;
  }

  changeEmail() {
    this.userService.changeEmail(this.emailFormControl.value);
    this.matDialog.closeAll();
  }
}
