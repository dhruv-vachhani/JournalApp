package com.dhruv.journalApp.controller;

import com.dhruv.journalApp.entity.User;
import com.dhruv.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @PostMapping("create-user")
    public ResponseEntity<User> createEntry(@RequestBody User user)
    {
        userService.saveNewUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
