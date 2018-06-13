package com.zhlzzz.together.controllers.chat_room;

import com.zhlzzz.together.controllers.user.UserView;
import com.zhlzzz.together.user.User;
import com.zhlzzz.together.user.user_game_config.UserGameConfigEntity;
import com.zhlzzz.together.user.user_label.UserLabelEntity;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(description = "用户")
public class UserChatRoomView extends UserView {

    private final UserGameConfigEntity userGameConfigEntity;

    public UserChatRoomView(User user, List<UserLabelEntity> userLabelEntitys, UserGameConfigEntity userGameConfigEntity) {
        super(user, userLabelEntitys);
        this.userGameConfigEntity = userGameConfigEntity;
    }

    public String getUserGameNickName() { return userGameConfigEntity.getNickname(); }
}
