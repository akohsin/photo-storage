import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { LayoutComponent } from '../layout/layout.component';
import { LoginRoutingModule } from './login-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  MatButtonModule,
  MatCardModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatProgressSpinnerModule
} from '@angular/material';



@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    LoginRoutingModule,
    ReactiveFormsModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class LoginModule { }
