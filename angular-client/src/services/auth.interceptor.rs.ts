import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {tap} from 'rxjs/internal/operators';
import {Observable} from 'rxjs';
import {AuthService} from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {


  constructor(private authService: AuthService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const idToken = this.authService.token;
    if (idToken !== undefined && idToken !== null && idToken !== '' && req !== undefined) {
      const cloned = req.clone({headers: req.headers.set('Authorization', 'Bearer:' + idToken)});
      return next.handle(cloned).pipe(
        tap(event => {
        }, error => {
          if (error.status === 403 || error.status === 401) {
            this.authService.logout();
          }
          if (error.message === 'ERR_CONNECTION_REFUSED') {
            this.authService.logout();
          }
          // http response status code
          console.log('----response----');
          console.error('status code:');
          console.error(error.status);
          console.error(error.message);
          console.log('--- end of response---');

        })
      );
    } else {
      return next.handle(req);
    }
  }
}
