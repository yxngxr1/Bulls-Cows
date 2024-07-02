package com.bulls_cows.game.service;

import com.bulls_cows.game.entities.User;
import com.bulls_cows.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository)
    {
        this.repository = repository;
    }

    public List<User> getAllUsers()
    {
        return repository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = repository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        repository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found: " + username);  // Логирование
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("User found: " + username);  // Логирование
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
