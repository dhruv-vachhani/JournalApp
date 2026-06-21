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

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAll(@PathVariable String userName)
    {
        User user = userService.findbyUserName(userName);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName)
    {
        journalEntryService.saveEntry(myEntry);
        User user = userService.findbyUserName(userName);
        user.getJournalEntries().add(myEntry);
        userService.saveUser(user);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getEntrybyId(@PathVariable ObjectId myid)
    {
        Optional<JournalEntry> entry = journalEntryService.findbyId(myid);
        if(entry.isPresent())
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myid}")
    public JournalEntry updateEntrybyId(@PathVariable ObjectId myid, @RequestBody JournalEntry myEntry)
    {
        JournalEntry old = journalEntryService.findbyId(myid).orElse(null);
        if(old != null)
        {
            old.setTitle(myEntry.getTitle());
            old.setContent(myEntry.getContent());
        }
        else return null;
        journalEntryService.saveEntry(old);
        return old;
    }

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<?> deleteEntrybyId(@PathVariable ObjectId myid, @PathVariable String userName)
    {
        journalEntryService.deletebyId(myid, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
