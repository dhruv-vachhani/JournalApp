package com.dhruv.journalApp.service;

import com.dhruv.journalApp.entity.JournalEntry;
import com.dhruv.journalApp.entity.User;
import com.dhruv.journalApp.repository.UserRepository;
import com.dhruv.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    
    @Autowired
    UserRepository userRepository;

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    public User findbyUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }
}
