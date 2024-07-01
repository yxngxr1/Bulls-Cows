package com.bulls_cows.game.controllers;

import com.bulls_cows.game.entities.Player;
import com.bulls_cows.game.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserCotroller
{
    UserService service;

    public UserCotroller(UserService service)
    {
        this.service = service;
    }
    @GetMapping("/users")
    public List<Player> getAllUsers()
    {
        return service.getAllUsers();
    }
    @PostMapping("/users")
    public Player createUser(Player player)
    {
        System.out.println("----------------");
        System.out.println(player.getUsername() + " "+ player.getId());
        return service.createUser(player);
    }
}
