package com.dhruv.journalApp.controller;

import com.dhruv.journalApp.entity.JournalEntry;
import com.dhruv.journalApp.service.JournalEntryService;
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

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        return new ResponseEntity<>(journalEntryService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry)
    {
        journalEntryService.saveEntry(myEntry);
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

    @DeleteMapping("id/{myid}")
    public ResponseEntity<?> deleteEntrybyId(@PathVariable ObjectId myid)
    {
        journalEntryService.deletebyId(myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
