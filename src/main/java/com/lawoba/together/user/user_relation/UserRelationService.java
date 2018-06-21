package com.lawoba.together.user.user_relation;

import com.lawoba.together.data.Slice;
import com.lawoba.together.data.SliceIndicator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRelationService {

    Boolean addUserRelation(Long userId, Long toUserId, UserRelation.Relation relation);

    Boolean updateUserRelation(Long userId, Long toUserId, String remark, UserRelation.Relation relation);

    Optional<? extends UserRelation> getUserRelationByUserIdAndToUserId(Long userId, Long toUserId);

    List<? extends UserRelation> getUserRelationsByUserIdAndRelation(Long userId, UserRelation.Relation relation);

    Slice<? extends UserRelation, Integer> getUserRelationsByRelation(SliceIndicator<Integer> indicator, Long userId, UserRelation.Relation relation);

    Set<? extends UserRelation> getUserRelationsByUserIdsInAndRelation(Set<Long> userIds, UserRelation.Relation relation);

    UserRelation getRandomFriend(Long userId);

}
