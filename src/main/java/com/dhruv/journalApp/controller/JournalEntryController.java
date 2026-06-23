package com.dhruv.journalApp.controller;

import com.dhruv.journalApp.entity.JournalEntry;
import com.dhruv.journalApp.entity.User;
import com.dhruv.journalApp.service.JournalEntryService;
import com.dhruv.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<JournalEntry> journalEntries = journalEntryService.findJournalbyUserName(userName);
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.saveEntry(myEntry);
        User user = userService.findbyUserName(userName);
        user.getJournalEntries().add(myEntry);
        userService.saveUser(user);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getEntrybyId(@PathVariable ObjectId myid)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<JournalEntry> journalEntries = journalEntryService.findJournalbyUserName(userName);
        List<JournalEntry> entry = journalEntries.stream().filter(x -> x.getId().equals(myid)).toList();

        if(!entry.isEmpty())
            return new ResponseEntity<>(entry.getFirst(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<JournalEntry> updateEntrybyId(@PathVariable ObjectId myid, @RequestBody JournalEntry myEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        List<JournalEntry> journalEntries = journalEntryService.findJournalbyUserName(userName);
        List<JournalEntry> entry = journalEntries.stream().filter(x -> x.getId().equals(myid)).toList();

        JournalEntry old = entry.getFirst();
        if(!entry.isEmpty())
        {
            old.setTitle(myEntry.getTitle());
            old.setContent(myEntry.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteEntrybyId(@PathVariable ObjectId myid)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        journalEntryService.deletebyId(myid, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
