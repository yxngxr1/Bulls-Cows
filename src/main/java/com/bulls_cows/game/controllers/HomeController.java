package com.bulls_cows.game.controllers;


import com.bulls_cows.game.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    UserService service;
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        UserDetails currentUser = (UserDetails) session.getAttribute("user");
        if (currentUser != null) {
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("password", currentUser.getPassword());
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
        } else {
            return "redirect:/";
        }
        return "statistics";
    }
}



