package com.lawoba.together.game;

import com.google.common.base.Strings;
import com.lawoba.together.game.game_config.GameConfig;
import com.lawoba.together.game.game_config.GameConfigEntity;
import com.lawoba.together.game.game_config.GameConfigOptionEntity;
import com.lawoba.together.game.game_config.GameConfigRepository;
import com.lawoba.together.utils.CollectionUtils;
import com.lawoba.together.utils.EntityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GameTypeServiceImpl implements GameTypeService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final JdbcTemplate jdbc;
    private final GameTypeRepository gameTypeRepository;
    private final GameConfigRepository gameConfigRepository;

    @Override
    public GameType addGameType(GameTypeParam gameTypeParam) throws GameTypeNameUsedException {
        GameTypeEntity gameTypeEntity = new GameTypeEntity();
        if (!Strings.isNullOrEmpty(gameTypeParam.getName())) {
            gameTypeEntity.setName(gameTypeParam.getName());
        }
        if (!Strings.isNullOrEmpty(gameTypeParam.getImgUrl())) {
            gameTypeEntity.setImgUrl(gameTypeParam.getImgUrl());
        }
        if (!Strings.isNullOrEmpty(gameTypeParam.getBackImgUrl())) {
            gameTypeEntity.setBackImgUrl(gameTypeParam.getBackImgUrl());
        }
        if (!Strings.isNullOrEmpty(gameTypeParam.getLogo())) {
            gameTypeEntity.setLogo(gameTypeParam.getLogo());
        }
        if (gameTypeParam.getMaxMember() != null) {
            gameTypeEntity.setMaxMember(gameTypeParam.getMaxMember());
        }
        if (gameTypeParam.getHot() != null) {
            gameTypeEntity.setHot(gameTypeParam.getHot());
        }
        gameTypeEntity.setCreateTime(LocalDateTime.now());

        try {
            return gameTypeRepository.save(gameTypeEntity);
        } catch (DataIntegrityViolationException e) {
            throw new GameTypeNameUsedException(gameTypeParam.getName(), e);
        }
    }

    @Override
    public GameType updateGameType(Integer id, GameTypeParam gameTypeParam) throws GameTypeNotFoundException, GameTypeNameUsedException {
        GameTypeEntity gameTypeEntity = gameTypeRepository.getById(id).orElseThrow(() -> new GameTypeNotFoundException(id));

        if (!Strings.isNullOrEmpty(gameTypeParam.getName())) {
            gameTypeEntity.setName(gameTypeParam.getName());
        }
        if (!Strings.isNullOrEmpty(gameTypeParam.getImgUrl())) {
            gameTypeEntity.setImgUrl(gameTypeParam.getImgUrl());
        }
        if (!Strings.isNullOrEmpty(gameTypeParam.getBackImgUrl())) {
            gameTypeEntity.setBackImgUrl(gameTypeParam.getBackImgUrl());
        }
        if (!Strings.isNullOrEmpty(gameTypeParam.getLogo())) {
            gameTypeEntity.setLogo(gameTypeParam.getLogo());
        }
        if (gameTypeParam.getMaxMember() != null) {
            gameTypeEntity.setMaxMember(gameTypeParam.getMaxMember());
        }
        if (gameTypeParam.getHot() != null) {
            gameTypeEntity.setHot(gameTypeParam.getHot());
        }
        if (gameTypeParam.getDeleted() != null) {
            gameTypeEntity.setDeleted(gameTypeParam.getDeleted());
        }
        try {
            return gameTypeRepository.save(gameTypeEntity);
        } catch (DataIntegrityViolationException e) {
            throw new GameTypeNameUsedException(gameTypeParam.getName(), e);
        }
    }

    @Override
    public Optional<? extends GameType> getGameTypeById(Integer id) {
        return gameTypeRepository.getById(id);
    }

    @Override
    public List<? extends GameType> getAllGameTypes() {
        return gameTypeRepository.findAll();
    }

    @Override
    public List<? extends GameConfig> getGameTypeConfigs(Integer gameTypeId) {
        List<GameConfigEntity> gameConfigEntities = em.createQuery("SELECT f FROM GameConfigEntity f WHERE f.gameTypeId = :gameTypeId ORDER BY f.id ASC", GameConfigEntity.class)
                .setParameter("gameTypeId", gameTypeId)
                .getResultList();
        val configMap = new HashMap<Long, GameConfigImpl>();
        val configIds = new ArrayList<Long>(gameConfigEntities.size());
        val topConfigs = new ArrayList<GameConfigImpl>();

        for (GameConfigEntity gameConfigEntity : gameConfigEntities) {
            GameConfigImpl gameConfig = new GameConfigImpl(gameConfigEntity, new ArrayList<>());
            configMap.put(gameConfig.getId(), gameConfig);
            configIds.add(gameConfig.getId());
            topConfigs.add(gameConfig);
        }
        if (!configIds.isEmpty()) {
            loadOptions(configMap, configIds);
        }
        return CollectionUtils.map(topConfigs, GameConfigImpl::toDto);
    }

    @Override
    public Optional<GameConfig> getGameTypeOtherConfig(Integer gameTypeId) {
        return gameConfigRepository.getFirstByGameTypeId(gameTypeId);
    }

    private void loadOptions(Map<Long, GameConfigImpl> configMap, List<Long> configIds) {
        val options = em.createQuery("SELECT o from GameConfigOptionEntity o WHERE o.configId IN (:configIds) ORDER BY o.configId ASC, o.id ASC", GameConfigOptionEntity.class)
                .setParameter("configIds", configIds)
                .getResultList();
        GameConfigImpl config = null;
        for (GameConfigOptionEntity option : options) {
            config = configMap.get(option.getConfigId());
            if (config == null) {
                throw new RuntimeException("option entity's field should in the map.");
            }
            CollectionUtils.add(config.getOptions(), option);
        }
    }

    @Override
    public void delete(Integer id) {
        tt.execute((s)-> {
            em.createQuery("DELETE FROM GameTypeEntity u WHERE u.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return true;
        });
    }

    @PostConstruct
    public void onStartUp() {
        if (!EntityUtils.isEntitiesEmpty(em, GameTypeEntity.class)) {
            return;
        }

        try {
            Resource resource = new ClassPathResource("game_type.sql");
            byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String sql = new String(bytes, Charset.forName("UTF-8"));

            jdbc.execute(sql);
        } catch (Throwable e) {
            log.error("can not load game_type sql.", e);
        }

    }
}
