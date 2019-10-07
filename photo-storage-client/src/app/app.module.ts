import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {MatIconModule} from '@angular/material/icon';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatSliderModule, MatToolbarModule, MatTooltipModule} from '@angular/material';
import { HeaderComponent } from './header/header.component';
import { PhotosComponent } from './photos/photos.component';
import { PhotoComponent } from './photo/photo.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PhotosComponent,
    PhotoComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    MatIconModule,
    MatTooltipModule,
    MatToolbarModule,
    MatSliderModule,
    AppRoutingModule,
    NoopAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
