import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import {AuthService} from '../services/auth.service';
import {AuthGuard} from '../services/auth-guard';
import {UserService} from '../services/user.service';
import {MatButtonModule, MatDialogModule, MatIconModule} from '@angular/material';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,
    NoopAnimationsModule
  ],
  providers: [AuthService, AuthGuard, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
