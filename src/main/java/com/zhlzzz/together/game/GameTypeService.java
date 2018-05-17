package com.zhlzzz.together.game;

import com.zhlzzz.together.game.game_config.GameConfig;

import java.util.List;
import java.util.Optional;

public interface GameTypeService {
    GameType addGameType(String name, String imgUrl, Boolean hot) throws GameTypeNameUsedException;

    GameType updateGameType(Integer id, String name, String imgUrl, Boolean hot) throws GameTypeNotFoundException, GameTypeNameUsedException;

    Optional<? extends GameType> getGameTypeById(Integer id);

    List<? extends GameType> getAllGameTypes();

    void delete(Integer id);

    List<? extends GameConfig> getGameTypeConfig(Integer gameTypeId);
}
