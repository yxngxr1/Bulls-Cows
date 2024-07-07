package com.bulls_cows.game.controllers;

import com.bulls_cows.game.entities.GameStatistics;
import com.bulls_cows.game.service.GameStatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game-stats")
public class GameStatController {
    @Autowired
    private GameStatService gameStatService;

    @PostMapping
    public ResponseEntity<String> saveGameStat(@RequestBody GameStatistics gameStatistics, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("user");

        if (currentUser != null) {
            gameStatService.saveGameStat(currentUser.getUsername(), gameStatistics);
            return new ResponseEntity<>("Game stat saved successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
    }
}

