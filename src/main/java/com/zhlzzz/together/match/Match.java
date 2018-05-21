package com.zhlzzz.together.match;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface Match extends Serializable {
    Long getId();
    Long getUserId();
    Integer getGameTypeId();
    LocalDateTime getCreateTime();
    LocalDateTime getExpiration();
    boolean isFinished();
    boolean isDeleted();
    boolean isEffective();
}
