package com.bulls_cows.game.controllers;

import com.bulls_cows.game.dto.ApiMessageResponse;
import com.bulls_cows.game.entities.GameStatistics;
import com.bulls_cows.game.service.GameStatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api")
public class GameStatController {
    @Autowired
    private GameStatService gameStatService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @PostMapping("/start-game")
    public Map<String, Object> startGame(HttpSession session, @RequestBody Map<String, Integer> request) {
        String secretNumber = generateSecretNumber();
        System.out.println(secretNumber);
        session.setAttribute("secretNumber", Base64.getEncoder().encodeToString(secretNumber.getBytes(StandardCharsets.UTF_8)));
        session.setAttribute("attempts", 0);
        session.setAttribute("startTime", System.currentTimeMillis());
        session.setAttribute("maxAttempts", request.get("maxAttempts"));
        session.setAttribute("maxCompletionTime", request.get("maxCompletionTime"));
        session.setAttribute("isWin", false);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Game started");
        return response;
    }

    @PostMapping("/make-guess")
    public Map<String, Object> makeGuess(HttpSession session, @RequestBody Map<String, String> request) {
        String secretNumber = new String(Base64.getDecoder().decode((String) session.getAttribute("secretNumber")), StandardCharsets.UTF_8);
        System.out.println(secretNumber);
        Map<String, Object> response = new HashMap<>();
        if (secretNumber == null) {
            response.put("error", "Game not found");
            return response;
        }

        int attempts = (int) session.getAttribute("attempts") + 1;
        session.setAttribute("attempts", attempts);

        String guess = request.get("guess");
        Map<String, Integer> result = getBullsAndCows(guess, secretNumber);

        boolean isWin = result.get("bulls") == 4;
        System.out.println(attempts);
        System.out.println(session.getAttribute("maxAttempts"));

        if (    isWin ||
                attempts > (int) session.getAttribute("maxAttempts") ||
                (System.currentTimeMillis() - (long) session.getAttribute("startTime")) / 1000 >= (int) session.getAttribute("maxCompletionTime"))
        {
            saveGameStatistics(session, isWin, attempts);
//            session.invalidate();
        } else
        {
            session.setAttribute("isWin", isWin);
        }

        response.put("bulls", result.get("bulls"));
        response.put("cows", result.get("cows"));
        response.put("attempts", attempts);
        response.put("isWin", isWin);
        response.put("maxAttempts", (int) session.getAttribute("maxAttempts"));
        return response;
    }

    private String generateSecretNumber() {
        List<Integer> digits = new ArrayList<>();
        Random random = new Random();
        while (digits.size() < 4) {
            int digit = random.nextInt(10);
            if (!digits.contains(digit)) {
                digits.add(digit);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int digit : digits) {
            sb.append(digit);
        }
        return sb.toString();
    }

    private Map<String, Integer> getBullsAndCows(String guess, String secret) {
        int bulls = 0, cows = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secret.charAt(i)) {
                bulls++;
            } else if (secret.contains(Character.toString(guess.charAt(i)))) {
                cows++;
            }
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("bulls", bulls);
        result.put("cows", cows);
        return result;
    }

    private void saveGameStatistics(HttpSession session, boolean isWin, int attempts)
    {

        UserDetails currentUser = (UserDetails) session.getAttribute("user");

        if (currentUser != null) {
            GameStatistics gameStatistics = new GameStatistics();
            gameStatistics.setAttempts(attempts);
            gameStatistics.setCompletionTime((int) ((System.currentTimeMillis() - (long) session.getAttribute("startTime")) / 1000));
            gameStatistics.setIsWin(isWin);
            gameStatistics.setMaxAttempts((int) session.getAttribute("maxAttempts"));
            gameStatistics.setMaxCompletionTime((int) session.getAttribute("maxCompletionTime"));

            gameStatService.saveGameStat(currentUser.getUsername(), gameStatistics);
        }
    }


}

