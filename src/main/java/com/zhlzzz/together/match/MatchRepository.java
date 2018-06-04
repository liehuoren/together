package com.zhlzzz.together.match;

import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MatchRepository extends Repository<MatchEntity, Long> {
    MatchEntity save(MatchEntity matchEntity);
    Optional<MatchEntity> getFirstByUserIdAndDeletedOrderByCreateTimeDesc(Long userId, boolean deleted);
    Optional<MatchEntity> getFirstByUserIdOrderByCreateTimeDesc(Long userId);
    Optional<MatchEntity> getById(Long id);
}
