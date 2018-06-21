package com.zhlzzz.together.controllers.chat_room;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zhlzzz.together.chat_room.ChatRoom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@ApiModel(description = "游戏配置字段")
@JsonPropertyOrder({"id","label","inputType","required","options"})
@RequiredArgsConstructor
public class ChatRoomView {

    private final ChatRoom chatRoom;
    private final List<UserChatRoomView> userViews;

    public Long getId() { return chatRoom.getId(); }

    public String getName() { return chatRoom.getName(); }

    @ApiModelProperty(name="创建时间",example = "2018/06/01 12:00:00")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+0800")
    public LocalDateTime getCreateTime() { return chatRoom.getCreateTime(); }

    public Boolean isClosed() { return LocalDateTime.now().isAfter(chatRoom.getCreateTime().plusHours(2)); }

    public List<UserChatRoomView> getUsers() {
        return userViews;
    }
}
