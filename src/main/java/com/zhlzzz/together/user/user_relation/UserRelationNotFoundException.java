package com.zhlzzz.together.user.user_relation;

import javax.annotation.Nullable;

public class UserRelationNotFoundException extends RuntimeException {

    private Long userId;

    private Long toUserId;

    public UserRelationNotFoundException(@Nullable Long userId, @Nullable Long toUserId) {
        super(String.format("找不到指定用户之间的关系（userId: %d）and（toUserId：%d）", userId, toUserId));
        this.userId = userId;
        this.toUserId = toUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getToUserId() { return toUserId; }


}
