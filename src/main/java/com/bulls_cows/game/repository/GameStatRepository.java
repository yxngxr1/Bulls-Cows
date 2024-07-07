package com.bulls_cows.game.repository;

import com.bulls_cows.game.entities.GameStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatRepository extends JpaRepository<GameStatistics, Long>{
    List<GameStatistics> findAllByUserId(Long userId);
}
