package com.zhlzzz.together.controllers.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import com.zhlzzz.together.user.user_label.UserLabelEntity;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;

import java.util.List;

@ApiModel(description = "用户")
@JsonPropertyOrder({"id","nickName","avatarUrl","gender"})
public class UserListView extends UserView {

    private final UserGameConfigEntity userGameConfigEntity;

    public UserListView(User user, List<UserLabelEntity> userLabelEntitys, UserGameConfigEntity userGameConfigEntity) {
        super(user, userLabelEntitys);
        this.userGameConfigEntity = userGameConfigEntity;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGameNickName() {
        if (userGameConfigEntity == null) {
            return null;
        }
        return userGameConfigEntity.getNickname();
    }

}
