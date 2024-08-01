package com.pratik.journalApplication.controller;

import com.pratik.journalApplication.entity.User;
import com.pratik.journalApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody User user){
        userService.createNewUser(user);
    }

    @GetMapping("/get-all-users")
    public List<User> getAll(){
        return userService.getAll();
    }



}
