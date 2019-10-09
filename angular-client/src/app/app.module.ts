import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {AuthService} from '../services/auth.service';
import {AuthGuard} from '../services/auth-guard';
import {UserService} from '../services/user.service';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule} from '@angular/material';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthInterceptor} from '../services/auth.interceptor.rs';
import {PhotoDisplayComponent} from './dialogs/photo-display-dialog/photo-display-component';

@NgModule({
  declarations: [
    PhotoDisplayComponent,
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,
    NoopAnimationsModule
  ],
  providers: [AuthService, AuthGuard, UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  entryComponents:[
    PhotoDisplayComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
