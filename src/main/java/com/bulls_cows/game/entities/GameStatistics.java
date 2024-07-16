package com.bulls_cows.game.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_game_stat")
public class GameStatistics
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer attempts;
    private Integer completionTime;
    private Integer maxAttempts;
    private Integer maxCompletionTime;
    private Boolean isWin;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Integer getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Integer getMaxCompletionTime() {
        return maxCompletionTime;
    }

    public void setMaxCompletionTime(int maxCompletionTime) {
        this.maxCompletionTime = maxCompletionTime;
    }

    public Boolean getIsWin() {
        return isWin;
    }

    public void setIsWin(Boolean win) {
        isWin = win;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "GameStatistics{" +
                "id=" + id +
                ", attempts=" + attempts +
                ", completionTime=" + completionTime +
                ", maxAttempts=" + maxAttempts +
                ", maxCompletionTime=" + maxCompletionTime +
                ", isWin=" + isWin +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
