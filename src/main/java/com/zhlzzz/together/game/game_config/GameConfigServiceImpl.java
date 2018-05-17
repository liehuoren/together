package com.zhlzzz.together.game.game_config;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class GameConfigServiceImpl implements GameConfigService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;

    private final GameConfigRepository gameConfigRepository;
    private final GameConfigOptionRepository gameConfigOptionRepository;

    @Override
    public GameConfigEntity addGameConfig(Integer gameTypeId, GameConfig.InputType inputType, String label, Boolean required) {
        GameConfigEntity gameConfigEntity = new GameConfigEntity();
        gameConfigEntity.setGameTypeId(gameTypeId);
        gameConfigEntity.setInputType(inputType);
        gameConfigEntity.setLabel(label);
        gameConfigEntity.setRequired(required);
        return gameConfigRepository.save(gameConfigEntity);
    }

    @Override
    public GameConfigEntity updateGameConfig(Long id, GameConfig.InputType inputType, String label, Boolean required) {
        GameConfigEntity gameConfigEntity = gameConfigRepository.findById(id).orElseThrow(() -> new GameConfigNotFoundException());
        if (inputType != null) {
            gameConfigEntity.setInputType(inputType);
        }
        if (!Strings.isNullOrEmpty(label)) {
            gameConfigEntity.setLabel(label);
        }
        if (required != null) {
            gameConfigEntity.setRequired(required);
        }
        return gameConfigRepository.save(gameConfigEntity);
    }

    @Override
    public GameConfigOptionEntity addOption(Long configId, String value) {
        GameConfigOptionEntity option = new GameConfigOptionEntity();
        option.setConfigId(configId);
        option.setValue(value);
        return gameConfigOptionRepository.save(option);
    }

    @Override
    public GameConfigOptionEntity updateOption(Long id, Long configId, String value) {
        GameConfigOptionEntity option = gameConfigOptionRepository.findById(id).orElseThrow(() -> new GameConfigOptionNotFoundException());
        if (configId != null) {
            option.setConfigId(configId);
        }
        if (!Strings.isNullOrEmpty(value)) {
            option.setValue(value);
        }
        return gameConfigOptionRepository.save(option);
    }

    @Override
    public void deleteGameConfig(Long id) {
        gameConfigRepository.deleteById(id);
    }

    @Override
    public void deleteOption(Long id) {
        gameConfigOptionRepository.deleteById(id);
    }
}
