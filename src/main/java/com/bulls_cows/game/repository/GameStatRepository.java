package com.bulls_cows.game.repository;

import com.bulls_cows.game.entities.GameStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStatRepository
    extends JpaRepository<GameStatistics, Long>{
}