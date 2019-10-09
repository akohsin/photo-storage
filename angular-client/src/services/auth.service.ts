import {Injectable} from '@angular/core';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedIn = false;
  token: string;
  email: string;

  constructor() {
  }

  authorize(token: string, email: string) {
    this.token = token;
    this.loggedIn = true;
    this.email = email;
  }

  logout() {
    this.token = '';
    this.loggedIn = false;
  }
}
