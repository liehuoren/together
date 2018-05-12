package com.zhlzzz.together.user;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class UserParam implements Serializable {

    protected String openId;

    protected String unionId;

    protected String nickName;

    protected String avatarUrl;

    protected Integer gender;

}
