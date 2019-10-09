import {Injectable} from '@angular/core';
import {AbstractControl} from '@angular/forms';
import {environment} from '../environments/environment';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient, private authService: AuthService, private router: Router) {
  }

  passwordValidator(control: AbstractControl) {
    if (control.value.match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,20}$/)) {
      return null;
    } else {
      return {invalidPassword: true};
    }
  }

  emailValidator(control: AbstractControl) {
    if (control.value.match(/^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/)) {
      return null;
    } else {
      return {email: true};
    }
  }

  signIn(email: any, password: any) {
    this.registerToApi({email, password}).subscribe((response: HttpResponse<any>) => {
      if (response.status === 226) {
        alert('Email is already used');
      } else {
      const token = response.headers.get('Authorization');
      this.authService.authorize(token, email);
      this.router.navigate(['/']);
      alert('Success');}
    }, (error1: HttpErrorResponse) => {
      alert('Password or email doesnt match the pattern');
    });
  }

  login(email: any, password: any) {
    this.loginToApi({email, password}).subscribe((response: HttpResponse<any>) => {
      this.authService.authorize(response.headers.get('Authorization'), email);
      this.router.navigate(['/']);
      alert('Success');
    }, () => {
      alert('Wrong password or no such user');
    });
  }

  changeEmail(email: string) {
    this.updateEmailWithApi(email).subscribe((response) => {
      if (response.status === 226) {
        alert('Email is already used');
      } else {
        const token = response.headers.get('Authorization');
        this.authService.authorize(token, email);
        alert('Success, e-mail is changed');
      }
    }, () => alert('Error'));
  }

  public loginToApi(appUser: AuthorizationDto): Observable<HttpResponse<any>> {
    const apiUrl = `http://${environment.ip}:${environment.port}/login`;
    return this.http.post<any>(apiUrl, appUser, {observe: 'response'});
  }

  public registerToApi(appUser: AuthorizationDto): Observable<HttpResponse<any>> {
    const apiUrl = `http://${environment.ip}:${environment.port}/user/register`;
    return this.http.put<any>(apiUrl, appUser, {observe: 'response'});
  }

  public updateEmailWithApi(newEmail: string): Observable<HttpResponse<any>> {
    const apiUrl = `http://${environment.ip}:${environment.port}/user/changeEmail`;
    return this.http.post<any>(apiUrl, newEmail, {observe: 'response'});
  }
}

export interface AuthorizationDto {
  email: string;
  password?: string;
}
