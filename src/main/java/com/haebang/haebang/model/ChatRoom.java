package com.haebang.haebang.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;
    private String roomId;
    private String name;
    private String username;

    public static ChatRoom create(String name,String username) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        chatRoom.username=username;
        return chatRoom;
    }
}