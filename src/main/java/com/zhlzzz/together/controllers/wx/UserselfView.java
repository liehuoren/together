package com.zhlzzz.together.controllers.wx;

import com.zhlzzz.together.controllers.user.UserView;
import com.zhlzzz.together.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户本人")
public class UserselfView extends UserView {

    private final User user;

    public UserselfView(User user) {
        super(user);
        this.user = user;
    }

    @ApiModelProperty(value = "用户openId", example = "1111111")
    public String getOpenId() { return user.getOpenId(); }
}
