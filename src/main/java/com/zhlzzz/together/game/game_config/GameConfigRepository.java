package com.zhlzzz.together.game.game_config;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameConfigRepository extends JpaRepository<GameConfigEntity, Long> {
    Optional<GameConfig> getFirstByGameTypeId(Integer gameTypeId);
}
