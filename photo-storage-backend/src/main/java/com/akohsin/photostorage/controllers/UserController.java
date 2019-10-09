package com.akohsin.photostorage.controllers;

import com.akohsin.photostorage.dto.AuthorizationDto;
import com.akohsin.photostorage.entities.User;
import com.akohsin.photostorage.security.SecurityConstants;
import com.akohsin.photostorage.security.TokenGenerator;
import com.akohsin.photostorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "/user/")
public class UserController {

    private UserService userService;
    private TokenGenerator tokenGenerator;

    @Autowired
    public UserController(UserService userService, TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
        this.userService = userService;
    }

    @PutMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody AuthorizationDto authorizationDto, HttpServletRequest request) {
        Optional<User> userByNewEmail = userService.getByEmail(authorizationDto.getEmail());
        if (userByNewEmail.isPresent()) return ResponseEntity.status(HttpStatus.IM_USED).build();
        if (this.userService.createUser(authorizationDto)) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(SecurityConstants.HEADER_STRING, tokenGenerator.generate(request, authorizationDto.getEmail()));
            return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(path = "/changeEmail")
    public ResponseEntity<String> changeEmail(@RequestBody String newEmail, HttpServletRequest request) {
        Optional<User> userByToken = userService.getByToken(request);
        Optional<User> userByNewEmail = userService.getByEmail(newEmail);
        if (userByNewEmail.isPresent()) return ResponseEntity.status(HttpStatus.IM_USED).build();
        if (userByToken.isPresent()) {
            this.userService.updateEmail(newEmail, userByToken.get());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(SecurityConstants.HEADER_STRING, tokenGenerator.generate(request, newEmail));
            return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).build();
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
