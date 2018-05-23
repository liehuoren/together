package com.zhlzzz.together.controllers.wx;

import com.zhlzzz.together.controllers.user.UserView;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.user_label.UserLabelEntity;
import com.zhlzzz.together.utils.CollectionUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

@ApiModel(description = "用户本人")
public class UserselfView extends UserView {

    private final User user;
    private final Set<UserLabelEntity> userLabelEntitys;
    private Set<UserLabelView> userLabelViews;

    public UserselfView(User user, Set<UserLabelEntity> userLabelEntitys) {
        super(user);
        this.user = user;
        this.userLabelEntitys = userLabelEntitys;
    }

    @ApiModelProperty(value = "用户openId", example = "1111111")
    public String getOpenId() { return user.getOpenId(); }

    public Set<UserLabelView> getUserLabelViews() {
        if (userLabelEntitys == null) {
            return null;
        }
        if (userLabelViews == null) {
            userLabelViews = CollectionUtils.map(userLabelEntitys, UserLabelView::new);
        }
        return userLabelViews;
    }
}
