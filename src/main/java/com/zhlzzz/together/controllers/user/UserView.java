package com.zhlzzz.together.controllers.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

@ApiModel(description = "用户")
@JsonPropertyOrder({"id","openId","nickName","avatarUrl","gender"})
public class UserView {

    @NonNull
    private final User user;

    public UserView(User user) {
        this.user = user;
    }

    @ApiModelProperty(value = "用户id", example = "1")
    public Long getId() { return user.getId(); }

    @ApiModelProperty(value = "用户昵称", example = "小星星")
    public String getNickName() { return user.getNickName(); }

    @ApiModelProperty(value = "用户头像url", example = "http://www.wechat.com")
    public String getAvatarUrl() { return user.getAvatarUrl(); }

    @ApiModelProperty(value = "用户性别", example = "1")
    public Integer getGender() { return user.getGender(); }
}
