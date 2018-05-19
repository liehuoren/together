package com.zhlzzz.together.rank.player;

import org.springframework.data.repository.Repository;

public interface PlayerRepository extends Repository<PlayerEntity, Long> {

    PlayerEntity save(PlayerEntity playerEntity);
//    Optional<PalyerEntity> findById(Long id);
//    Set<PalyerEntity> findAll();
}
