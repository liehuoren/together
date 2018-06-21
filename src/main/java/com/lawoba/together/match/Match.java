package com.lawoba.together.match;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Match extends Serializable {
    enum Range {
        onlyFriend, includeFriend, otherFriend
    }
    Long getId();
    Long getUserId();
    Integer getGameTypeId();
    String getFormId();
    String getName();
    String getOtherItem();
    Integer getMemberNum();
    Integer getMinute();
    Range getMatchRange();
    Long getRoomId();
    LocalDateTime getCreateTime();
    LocalDateTime getExpiration();
    boolean isFinished();
    boolean isDeleted();
    boolean isEffective();
    boolean isCloseDown();
}
