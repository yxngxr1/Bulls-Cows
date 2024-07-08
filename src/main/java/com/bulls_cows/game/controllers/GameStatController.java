package com.bulls_cows.game.controllers;

import com.bulls_cows.game.dto.ApiMessageResponse;
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
@RequestMapping("/api")
public class GameStatController {
    @Autowired
    private GameStatService gameStatService;

    @PostMapping("/game-stat")
    public ResponseEntity<ApiMessageResponse> saveGameStat(@RequestBody GameStatistics gameStatistics, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("user");

        if (currentUser != null) {
            System.out.println(gameStatistics);
            gameStatService.saveGameStat(currentUser.getUsername(), gameStatistics);
            ApiMessageResponse response = new ApiMessageResponse("Game stat saved successfully");
            return ResponseEntity.ok(response);
        } else {
            ApiMessageResponse response = new ApiMessageResponse("User not authenticated");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}

