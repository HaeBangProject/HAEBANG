package com.haebang.haebang.controller;

import com.haebang.haebang.model.ChatMessage;
import com.haebang.haebang.repository.ChatRoomRepository;
import com.haebang.haebang.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;


    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessage message){

        chatRoomRepository.enterChatRoom(message.getRoomId());
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");

        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage message){
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    @MessageMapping(value="/chat/exit")
    public void exit(ChatMessage message){
        message.setMessage(message.getWriter() + "님이 채팅방을 종료하셨습니다.");

        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);

    }
}