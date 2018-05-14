package com.zhlzzz.together.user.user_relation;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRelationRepository extends Repository<UserRelationEntity, Long> {
    Optional<? extends UserRelation> save(UserRelationEntity userRelationEntity);
    List<UserRelation> findByUserIdAndRelation(Long userId, UserRelation.Relation relation);
}
