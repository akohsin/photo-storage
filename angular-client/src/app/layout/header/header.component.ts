import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {UserService} from '../../../services/user.service';
import {MatDialog} from '@angular/material';
import {EmailDialogComponent} from '../../dialogs/email-dialog-component';
import {PhotoDialogComponent} from '../../dialogs/photo-dialog-component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthService, private userService: UserService, private matDialog: MatDialog) {
  }

  ngOnInit() {
  }

  changeEmail() {
    this.matDialog.open(EmailDialogComponent);
  }

  logout() {

  }

  uploadPhoto() {
    this.matDialog.open(PhotoDialogComponent);
  }
}
