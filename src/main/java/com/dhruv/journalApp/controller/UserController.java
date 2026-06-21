package com.dhruv.journalApp.controller;

import com.dhruv.journalApp.entity.JournalEntry;
import com.dhruv.journalApp.entity.User;
import com.dhruv.journalApp.service.JournalEntryService;
import com.dhruv.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createEntry(@RequestBody User user)
    {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName)
    {
        User user = userService.findbyUserName(userName);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUserByUserName(@PathVariable String userName, @RequestBody User user)
    {
        User dbUser = userService.findbyUserName(userName);
        if(dbUser != null)
        {
            dbUser.setUserName(user.getUserName());
            dbUser.setPassword(user.getPassword());
            userService.saveUser(dbUser);
            return new ResponseEntity<>(dbUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("/{userName}")
//    public ResponseEntity<?> deleteUserByUserName(@PathVariable String userName)
//    {
//        userService.deletebyId();
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
