package com.zhlzzz.together.user.user_game_config;

import java.util.Optional;
import java.util.Set;

public interface UserGameConfigService {

    void updateUserGameConfig(Long userId, Integer gameTypeId, UserGameConfigParam userGameConfigParam);

    Optional<UserGameConfigEntity> getUserGameConfigById(Long userId, Integer gameTypeId);

    Set<UserGameConfigEntity> getUserGameConfigsByGameType(Integer gameTypeId);

}
