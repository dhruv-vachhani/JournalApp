package com.dhruv.journalApp.controller;

import com.dhruv.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ArrayList<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry)
    {
        journalEntries.put(myEntry.getId(),myEntry);
        return true;
    }

    @GetMapping("id/{myid}")
    public JournalEntry getEntrybyId(@PathVariable long myid)
    {
        return journalEntries.get(myid);
    }

    @PutMapping("id/{myid}")
    public JournalEntry updateEntrybyId(@PathVariable long myid, @RequestBody JournalEntry myEntry)
    {
        return journalEntries.put(myid,myEntry);
    }

    @DeleteMapping("id/{myid}")
    public boolean deleteEntrybyId(@PathVariable long myid)
    {
        journalEntries.remove(myid);
        return true;
    }
}
