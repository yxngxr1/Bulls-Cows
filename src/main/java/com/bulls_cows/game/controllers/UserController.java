package com.bulls_cows.game.controllers;

import com.bulls_cows.game.entities.User;
import com.bulls_cows.game.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController
{
    @Autowired
    UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> getAllUsers()
    {
        return service.getAllUsers();
    }

    @PostMapping("/users")
    public boolean createUser(User user)
    {
        System.out.println("----------------");
        System.out.println(user.getUsername() + " "+ user.getId());
        return service.saveUser(user);
    }
}
