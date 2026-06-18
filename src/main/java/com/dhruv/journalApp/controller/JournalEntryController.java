package com.dhruv.journalApp.controller;

import com.dhruv.journalApp.entity.JournalEntry;
import com.dhruv.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll()
    {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry myEntry)
    {
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("id/{myid}")
    public JournalEntry getEntrybyId(@PathVariable ObjectId myid)
    {
        return journalEntryService.findbyId(myid).orElse(null);
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
    public boolean deleteEntrybyId(@PathVariable ObjectId myid)
    {
        journalEntryService.deletebyId(myid);
        return true;
    }
}
