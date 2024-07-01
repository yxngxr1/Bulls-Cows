package com.bulls_cows.game.service;

import com.bulls_cows.game.entities.Player;
import com.bulls_cows.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository)
    {
        this.repository = repository;
    }
    public List<Player> getAllUsers()
    {
        return repository.findAll();
    }

    public Player createUser(Player user)
    {
        return repository.save(user);
    }
}
