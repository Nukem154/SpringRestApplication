package com.nukem.nothing.controller;

import com.nukem.nothing.entity.User;
import com.nukem.nothing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        if(!user.getPassword().equals(user.getPasswordConfirm())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

}
