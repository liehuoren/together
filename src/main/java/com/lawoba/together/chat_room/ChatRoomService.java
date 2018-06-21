package com.lawoba.together.chat_room;

import java.util.Optional;
import java.util.Set;

public interface ChatRoomService {

    ChatRoom addChatRoom(String name, Integer gameTypeId, Set<Long> userIds);

    Optional<ChatRoom> getChatRoom(Long id);
}
