package com.zhlzzz.together.rank.player.player_season;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PlayerSeasonRepository extends Repository<PlayerSeasonEntity, Long> {

//    PlayerSeasonEntity save(PlayerSeasonEntity playerSeasonEntity);
    Optional<PlayerSeasonEntity> findByUserIdAndSeasonId(Long userId, String seasonId);
//    List<PlayerSeasonEntity> save(Iterable<PlayerSeasonEntity> entities);
}
