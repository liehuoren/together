package com.lawoba.together.user;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface User extends Serializable {

    enum Role {
        admin, user
    }
    Long getId();
    String getPhone();
    String getOpenId();
    String getUnionId();
    Role getRole();
    String getNickName();
    String getAvatarUrl();
    String getQRCode();
    Integer getGender();
    Integer getCreditScore();
    LocalDateTime getLastLoginTime();
    LocalDateTime getCreateTime();
    boolean isAdmin();
}