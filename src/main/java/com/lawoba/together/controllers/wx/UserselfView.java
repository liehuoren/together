package com.lawoba.together.controllers.wx;

import com.lawoba.together.controllers.user.UserView;
import com.lawoba.together.user.User;
import com.lawoba.together.user.user_label.UserLabelEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "用户本人")
public class UserselfView extends UserView {

    private final User user;

    public UserselfView(User user, List<UserLabelEntity> userLabelEntities) {
        super(user, userLabelEntities);
        this.user = user;
    }

    @ApiModelProperty(value = "用户二维码", example = "http://www.baidu.com")
    public String getQRCode() { return user.getQRCode(); }

    @ApiModelProperty(value = "用户openId", example = "1111111")
    public String getOpenId() { return user.getOpenId(); }

}
