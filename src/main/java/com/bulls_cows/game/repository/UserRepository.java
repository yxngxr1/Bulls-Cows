package com.bulls_cows.game.repository;

import com.bulls_cows.game.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
    extends JpaRepository<Player, Long> {

}
