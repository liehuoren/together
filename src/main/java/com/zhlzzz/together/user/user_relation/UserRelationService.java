package com.zhlzzz.together.user.user_relation;

import java.util.List;
import java.util.Optional;

public interface UserRelationService {

    void updateUserRelation(Long userId, Long toUserId, String remark, UserRelation.Relation relation);

    Optional<? extends UserRelation> getUserRelationByUserIdAndToUserId(Long userId, Long toUserId);

    List<? extends UserRelation> getUserRelationsByUserIdAndRelation(Long userId, UserRelation.Relation relation);

}
