package com.lawoba.together.match;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MatchRepository extends Repository<MatchEntity, Long> {
    MatchEntity save(MatchEntity matchEntity);
    Optional<MatchEntity> getFirstByUserIdOrderByCreateTimeDesc(Long userId);
    Optional<MatchEntity> getByUserIdAndRoomId(Long userId, Long roomId);
    Optional<MatchEntity> getById(Long id);
    List<MatchEntity> getByUserIdInAndDeleted(Set<Long> userIds, boolean deleted);

}