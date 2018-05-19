package com.zhlzzz.together.rank.player;

import org.springframework.data.repository.Repository;

public interface PalyerRepository extends Repository<PalyerEntity, Long> {

    PalyerEntity save(PalyerEntity palyerEntity);
//    Optional<PalyerEntity> findById(Long id);
//    Set<PalyerEntity> findAll();
}
