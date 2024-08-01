package com.pratik.journalApplication.controller;

import com.pratik.journalApplication.entity.JournalEntry;
import com.pratik.journalApplication.entity.User;
import com.pratik.journalApplication.service.JournalEntryService;
import com.pratik.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<JournalEntry> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        return user.getJournalEntryList() != null ? user.getJournalEntryList() : null;
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry, username);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(entry, HttpStatus.CREATED);

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntryList().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.deleteById(myId, username);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry entry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntryList().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            JournalEntry oldEntry = journalEntryService.findById(myId).orElse(null);
            if(oldEntry != null){
                oldEntry.setTitle(!entry.getTitle().isEmpty() ? entry.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>( oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
