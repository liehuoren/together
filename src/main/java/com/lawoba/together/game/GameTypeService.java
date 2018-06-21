package com.lawoba.together.game;

import com.lawoba.together.game.game_config.GameConfig;

import java.util.List;
import java.util.Optional;

public interface GameTypeService {
    GameType addGameType(GameTypeParam gameTypeParam) throws GameTypeNameUsedException;

    GameType updateGameType(Integer id, GameTypeParam gameTypeParam) throws GameTypeNotFoundException, GameTypeNameUsedException;

    Optional<? extends GameType> getGameTypeById(Integer id);

    List<? extends GameType> getAllGameTypes();

    List<? extends GameConfig> getGameTypeConfigs(Integer gameTypeId);

    Optional<GameConfig> getGameTypeOtherConfig(Integer gameTypeId);

    void delete(Integer id);
}
