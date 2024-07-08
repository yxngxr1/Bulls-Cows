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
    private Long completionTime;
    private Integer maxAttempts;
    private Long maxCompletionTime;

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

    public Long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Long getMaxCompletionTime() {
        return maxCompletionTime;
    }

    public void setMaxCompletionTime(Long maxCompletionTime) {
        this.maxCompletionTime = maxCompletionTime;
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
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
