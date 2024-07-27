package com.pratik.journalApplication.controller;

import com.pratik.journalApplication.entity.JournalEntry;
import com.pratik.journalApplication.entity.User;
import com.pratik.journalApplication.service.JournalEntryService;
import com.pratik.journalApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.createUser(user);
    }

    @PutMapping("/{userName}")
    public void updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb = userService.findByUsername(userName);
        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.createUser(userInDb);
        }
    }

}
