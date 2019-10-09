import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {UserService} from '../../../services/user.service';
import {MatDialog} from '@angular/material';
import {EmailDialogComponent} from '../../dialogs/email-dialog-component';
import {PhotoDialogComponent} from '../../dialogs/photo-upload-dialog/photo-dialog-component';
import {Router} from '@angular/router';
import {PhotoService} from '../../../services/photo.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  email = '';

  constructor(private authService: AuthService,
              private photoService: PhotoService,
              private router: Router, private userService: UserService, private matDialog: MatDialog) {
    this.email = this.authService.email;
  }

  ngOnInit() {
  }

  changeEmail() {
    this.matDialog.open(EmailDialogComponent);
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  uploadPhoto() {
    this.matDialog.open(PhotoDialogComponent);
  }

  search(event: any) {
    this.photoService.share('SEARCH_FOR:' + event.target.value);
  }
}
