package com.zhlzzz.together.user.user_game_config;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserGameConfigRepository extends Repository<UserGameConfigEntity, Long> {
    UserGameConfigEntity save(UserGameConfigEntity userGameConfigEntity);
    Optional<UserGameConfigEntity> getByUserIdAndGameTypeId(Long userid, Integer gameTypeId);
    Set<UserGameConfigEntity> getAllByGameTypeId(Integer gameTypeId);
    List<UserGameConfigEntity> getAllByUserIdAndGameTypeIdIn(Long userId, Set<Integer> gameTypeIds);
    List<UserGameConfigEntity> getAllByUserIdInAndGameTypeId(List<Long> userIds, Integer gameTypeId);
}
