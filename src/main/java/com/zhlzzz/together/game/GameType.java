package com.zhlzzz.together.game;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface GameType extends Serializable {

    Integer getId();
    String getName();
    String getImgUrl();
    String getLogo();
    Integer getMaxMember();
    Boolean isHot();
    Boolean isDeleted();
    LocalDateTime getCreateTime();
}
