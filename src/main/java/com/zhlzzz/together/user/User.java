package com.zhlzzz.together.user;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface User extends Serializable {

    Long getId();
    String getOpenId();
    String getUnionId();
    String getNickName();
    String getAvatarUrl();
    Integer getGender();
    LocalDateTime getLastLoginTime();
    LocalDateTime getCreateTime();
}
