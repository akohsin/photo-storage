import {Component} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  emailFormControl = new FormControl('', [
    Validators.required,
    this.userService.emailValidator,
  ]);
  passwordFormControl = new FormControl('', [this.userService.passwordValidator]);

  constructor(private userService: UserService) {
  }

  valid() {
    return this.emailFormControl.valid && this.passwordFormControl.valid;
  }

  login() {
    this.userService.login(this.emailFormControl.value, this.passwordFormControl.value);
  }

  signIn() {
    this.userService.signIn(this.emailFormControl.value, this.passwordFormControl.value);
  }
}
