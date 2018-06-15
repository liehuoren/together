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

    private final User friend;

    public UserChatRoomView(User user, List<UserLabelEntity> userLabelEntitys, UserGameConfigEntity userGameConfigEntity, User friend) {
        super(user, userLabelEntitys);
        this.userGameConfigEntity = userGameConfigEntity;
        this.friend = friend;
    }

    public String getUserGameNickName() { return userGameConfigEntity.getNickname(); }

    public String getFriendName() {
        if (friend == null) {
            return null;
        }
        return friend.getNickName();
    }

}
