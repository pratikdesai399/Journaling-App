package com.pratik.journalApplication.service;

import com.pratik.journalApplication.entity.JournalEntry;
import com.pratik.journalApplication.entity.User;
import com.pratik.journalApplication.repository.JournalEntryRepository;
import com.pratik.journalApplication.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
       return userRepository.findAll();
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
