package com.zhlzzz.together.game.game_config;

public interface GameConfigService {

    GameConfigEntity addGameConfig(Integer gameTypeId, GameConfig.InputType inputType, String label, Boolean required);

    GameConfigEntity updateGameConfig(Long id, GameConfig.InputType inputType, String label, Boolean required);

    GameConfigOptionEntity addOption(Long configId, String value);

    GameConfigOptionEntity updateOption(Long id, Long configId, String value);

    void deleteGameConfig(Long id);

    void deleteOption(Long id);


}
