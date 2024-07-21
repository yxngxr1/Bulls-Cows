package com.bulls_cows.game.service;

import com.bulls_cows.game.entities.GameStatistics;
import com.bulls_cows.game.entities.User;
import com.bulls_cows.game.repository.GameStatRepository;
import com.bulls_cows.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GameStatService
{
    @Autowired
    GameStatRepository gameStatRepository;

    @Autowired
    private UserRepository userRepository;

    public GameStatService(GameStatRepository gameStatRepository)
    {
        this.gameStatRepository = gameStatRepository;
    }

    public List<GameStatistics> findAllByUserId(Long userId)
    {
        return gameStatRepository.findAllByUserId(userId);
    }

    public void saveGameStat(String username, GameStatistics gameStatistics) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            GameStatistics gameStatistics1 = new GameStatistics();
            gameStatistics1.setAttempts(gameStatistics.getAttempts());
            gameStatistics1.setCompletionTime(gameStatistics.getCompletionTime());
            gameStatistics1.setMaxAttempts(gameStatistics.getMaxAttempts());
            gameStatistics1.setMaxCompletionTime(gameStatistics.getMaxCompletionTime());
            gameStatistics1.setIsWin(gameStatistics.getIsWin());
            gameStatistics1.setUser(user);

            gameStatRepository.save(gameStatistics1);
        }
    }
}
