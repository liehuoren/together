package com.zhlzzz.together.game;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class GameTypeServiceImpl implements GameTypeService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final GameTypeRepository gameTypeRepository;

    @Override
    public GameType addGameType(String name, String imgUrl, Boolean hot) throws GameTypeNameUsedException {
        GameTypeEntity gameTypeEntity = new GameTypeEntity();
        gameTypeEntity.setName(name);
        gameTypeEntity.setImgUrl(imgUrl);
        gameTypeEntity.setHot(hot);
        gameTypeEntity.setCreateTime(LocalDateTime.now());
        try {
            return gameTypeRepository.save(gameTypeEntity);
        } catch (DataIntegrityViolationException e) {
            throw new GameTypeNameUsedException();
        }
    }

    @Override
    public GameType updateGameType(Integer id, String name, String imgUrl, Boolean hot) throws GameTypeNotFoundException, GameTypeNameUsedException {
        GameTypeEntity gameTypeEntity = gameTypeRepository.getById(id).orElseThrow(() -> new GameTypeNotFoundException(id));

        if (!Strings.isNullOrEmpty(name)) {
            gameTypeEntity.setName(name);
        }
        if (!Strings.isNullOrEmpty(imgUrl)) {
            gameTypeEntity.setImgUrl(imgUrl);
        }
        if (hot != null) {
            gameTypeEntity.setHot(hot);
        }
        try {
            return gameTypeRepository.save(gameTypeEntity);
        } catch (DataIntegrityViolationException e) {
            throw new GameTypeNameUsedException();
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
    public void delete(Integer id) {
        GameTypeEntity gameTypeEntity = gameTypeRepository.getById(id).orElseThrow(() -> new GameTypeNotFoundException(id));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM GameTypeEntity u WHERE u.id = :id")
                    .setParameter("id", gameTypeEntity.getId())
                    .executeUpdate();
            return true;
        });
    }
}
