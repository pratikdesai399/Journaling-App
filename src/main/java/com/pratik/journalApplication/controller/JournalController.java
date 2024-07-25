package com.pratik.journalApplication.controller;

import com.pratik.journalApplication.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalController {
    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public void createEntry(@RequestBody JournalEntry entry){
        long id = entry.getId();
        String title = entry.getTitle();
        String content = entry.getContent();

        journalEntries.put(id, entry);
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntryById(@PathVariable Long myId){
        return journalEntries.get(myId);
    }

    @DeleteMapping("/id/{myId}")
    public void deleteJournalEntryById(@PathVariable Long myId){
        journalEntries.remove(myId);
    }

    @PutMapping("/id/{myId}")
    public void updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry entry){
        journalEntries.put(myId, entry);
    }
}
