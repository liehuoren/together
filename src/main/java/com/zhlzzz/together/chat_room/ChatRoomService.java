package com.zhlzzz.together.chat_room;

import java.util.Optional;
import java.util.Set;

public interface ChatRoomService {

    ChatRoom addChatRoom(String name, Set<String> openIds);

    Optional<ChatRoom> getChatRoom(Long id);
}
