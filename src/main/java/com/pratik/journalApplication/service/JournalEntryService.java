package com.pratik.journalApplication.service;

import com.pratik.journalApplication.entity.JournalEntry;
import com.pratik.journalApplication.entity.User;
import com.pratik.journalApplication.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAll(){
       return journalEntryRepository.findAll();
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userService.findByUsername(username);
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntryList().add(savedEntry);
            userService.createUser(user);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, String username){
        try{
            User user = userService.findByUsername(username);
            boolean didRemove = user.getJournalEntryList().removeIf(x -> x.getId().equals(id));
            userService.createUser(user);
            if(didRemove){
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
