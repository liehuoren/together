package com.zhlzzz.together.controllers.chat_room;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.chat_room.ChatRoom;
import io.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;

import java.util.List;


@ApiModel(description = "游戏配置字段")
@JsonPropertyOrder({"id","label","inputType","required","options"})
@RequiredArgsConstructor
public class ChatRoomView {

    private final ChatRoom chatRoom;
    private final List<UserChatRoomView> userViews;

    public Long getId() { return chatRoom.getId(); }

    public String getName() { return chatRoom.getName(); }

    public List<UserChatRoomView> getUsers() {
        return userViews;
    }
}
