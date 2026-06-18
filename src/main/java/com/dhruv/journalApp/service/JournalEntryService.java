package com.dhruv.journalApp.service;

import com.dhruv.journalApp.entity.JournalEntry;
import com.dhruv.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findbyId(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    public void deletebyId(ObjectId id)
    {
        journalEntryRepository.deleteById(id);
    }
}
