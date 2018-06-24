package com.lawoba.together.controllers.chat_room;

import com.lawoba.together.chat_room.ChatRoom;
import com.lawoba.together.chat_room.ChatRoomService;
import com.lawoba.together.controllers.ApiAuthentication;
import com.lawoba.together.controllers.ApiExceptions;
import com.lawoba.together.match.Match;
import com.lawoba.together.match.MatchService;
import com.lawoba.together.user.User;
import com.lawoba.together.user.UserService;
import com.lawoba.together.user.user_game_config.UserGameConfigEntity;
import com.lawoba.together.user.user_game_config.UserGameConfigService;
import com.lawoba.together.user.user_label.UserLabelEntity;
import com.lawoba.together.user.user_label.UserLabelService;
import com.lawoba.together.user.user_relation.UserRelation;
import com.lawoba.together.user.user_relation.UserRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/chat-room", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(description = "聊天室", tags = {"ChatRoom"})
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final UserLabelService userLabelService;
    private final UserGameConfigService userGameConfigService;
    private final UserRelationService userRelationService;
    private final MatchService matchService;

    @GetMapping(value = "/{id:\\d+}")
    @ApiOperation(value = "获取聊天室基本信息")
    @ResponseBody
    public ChatRoomView getChatRoom(@PathVariable Long id, ApiAuthentication auth) {
        User user = userService.getUserById(auth.requireUserId()).orElseThrow(() -> ApiExceptions.notFound("不存在此人"));
        ChatRoom chatRoom = chatRoomService.getChatRoom(id).orElseThrow(() -> ApiExceptions.notFound("没有相关房间信息"));
        Match match = matchService.getMatchByUserAndRoom(user.getId(), chatRoom.getId()).orElseThrow(() -> ApiExceptions.notFound("没有相关匹配"));
        if (match.isCloseDown()) {
            throw ApiExceptions.badRequest("房间已关闭");
        }
        if (!chatRoom.getUserIds().contains(user.getId())) {
            throw ApiExceptions.noPrivilege();
        }

        Set<? extends User> users = userService.getUsersByIds(chatRoom.getUserIds());

        List<UserChatRoomView> userViews = new ArrayList<>();

        for (User user1 : users) {
            userViews.add(buildUserView(user1, chatRoom.getGameTypeId()));
        }
        return new ChatRoomView(chatRoom, userViews);
    }

    private UserChatRoomView buildUserView(User user, Integer gameTypeId) {
        List<UserLabelEntity> userLabels = userLabelService.getUserLabelsByUserId(user.getId());
        UserGameConfigEntity userGameConfigEntity = userGameConfigService.getUserGameConfigByUserAndGameType(user.getId(), gameTypeId).orElse(null);
        UserRelation userRelation = userRelationService.getRandomFriend(user.getId());
        User friend = userService.getUserById(userRelation.getToUserId()).orElse(null);
        return new UserChatRoomView(user, userLabels, userGameConfigEntity, friend);
    }
}
