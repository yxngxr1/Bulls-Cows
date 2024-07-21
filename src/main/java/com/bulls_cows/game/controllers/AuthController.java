package com.bulls_cows.game.controllers;

import com.bulls_cows.game.entities.User;
import com.bulls_cows.game.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthController(UserService service) {this.userService = service;}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpServletRequest request, Model model) {
        UserDetails userDetails = null;
        try
        {
            userDetails = userService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                HttpSession session = request.getSession();
                session.setAttribute("user", userDetails);
                return "redirect:/";
            }
            else
            {
                model.addAttribute("error", "Invalid password.");
                return "redirect:/login?error";
            }
        }
        catch (UsernameNotFoundException e)
        {
            model.addAttribute("error", "User not found.");
            return "redirect:/login?error";
        }
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @PostMapping("/register")
    public String registerPlayer(User user) {
        if (userService.saveUser(user))
        {
            return "redirect:/login";
        }
        return "redirect:/register?error";
    }
}
