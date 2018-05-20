package com.zhlzzz.together.user.user_game_config;

import com.google.common.base.Strings;
import com.zhlzzz.together.game.GameTypeEntity;
import com.zhlzzz.together.game.GameTypeNotFoundException;
import com.zhlzzz.together.game.GameTypeRepository;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.UserNotFoundException;
import com.zhlzzz.together.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserGameConfigServiceImpl implements UserGameConfigService {

    @PersistenceContext
    private EntityManager em;
    private final TransactionTemplate tt;
    private final UserGameConfigRepository userGameConfigRepository;
    private final UserRepository userRepository;
    private final GameTypeRepository gameTypeRepository;


    @Override
    public void updateUserGameConfig(Long userId, Integer gameTypeId, UserGameConfigParam userGameConfigParam) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        GameTypeEntity gameType = gameTypeRepository.getById(gameTypeId).orElseThrow(() -> new GameTypeNotFoundException(gameTypeId));
        tt.execute((s)-> {
            em.createQuery("DELETE FROM UserGameConfigEntity v WHERE v.userId = :userId AND v.gameTypeId = :gameTypeId")
                    .setParameter("userId", user.getId())
                    .setParameter("gameTypeId", gameType.getId())
                    .executeUpdate();
            UserGameConfigEntity userGameConfigEntity = new UserGameConfigEntity();
            userGameConfigEntity.setUserId(user.getId());
            userGameConfigEntity.setGameTypeId(gameType.getId());
            if (!Strings.isNullOrEmpty(userGameConfigParam.getNickname())) {
                userGameConfigEntity.setNickname(userGameConfigParam.getNickname());
            }
            if (!Strings.isNullOrEmpty(userGameConfigParam.getContact())) {
                userGameConfigEntity.setContact(userGameConfigParam.getContact());
            }
            if (!Strings.isNullOrEmpty(userGameConfigParam.getArea())) {
                userGameConfigEntity.setArea(userGameConfigParam.getArea());
            }

            return userGameConfigRepository.save(userGameConfigEntity);
        });
    }

    @Override
    public Optional<UserGameConfigEntity> getUserGameConfigById(Long userId, Integer gameTypeId) {
        return null;
    }

    @Override
    public Set<UserGameConfigEntity> getUserGameConfigsByGameType(Integer gameTypeId) {
        return null;
    }
}
