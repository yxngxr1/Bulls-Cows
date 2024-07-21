package com.bulls_cows.game.controllers;


import com.bulls_cows.game.entities.GameStatistics;
import com.bulls_cows.game.entities.User;
import com.bulls_cows.game.repository.UserRepository;
import com.bulls_cows.game.service.GameStatService;
import com.bulls_cows.game.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameStatService gameStatService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        UserDetails currentUser = (UserDetails) session.getAttribute("user");
        if (currentUser != null) {
            User user = userRepository.findByUsername(currentUser.getUsername());
            model.addAttribute("username", user.getUsername());
            List<GameStatistics> gameStatistics = gameStatService.findAllByUserId(user.getId());
            model.addAttribute("gamesCount", gameStatistics.size());
            int countOfWins = 0;
            for(GameStatistics statistics: gameStatistics)
            {
                if(statistics.getIsWin())
                {
                    countOfWins++;
                }
            }
            model.addAttribute("winRate", String.format("%.1f", (((float) countOfWins / gameStatistics.size()) * 100)));
        }
        return "index";
    }

    @GetMapping("/game")
    public String game(HttpSession session, Model model) {
        UserDetails currentUser = (UserDetails) session.getAttribute("user");
        System.out.println("User found: " + currentUser);  // Логирование
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getUsername());
        } else {
            return "redirect:/";
        }
        return "game";
    }

    @GetMapping("/statistics")
    public String statistics(HttpSession session, Model model) {
        UserDetails currentUser = (UserDetails) session.getAttribute("user");
        System.out.println("User found: " + currentUser);  // Логирование
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getUsername());

            User user = userRepository.findByUsername(currentUser.getUsername());
            System.out.println(user);
            List<GameStatistics> gameStats = gameStatService.findAllByUserId(user.getId());
            System.out.println(gameStats);
            model.addAttribute("gameStats", gameStats);
        } else {
            return "redirect:/";
        }
        return "statistics";
    }
}



