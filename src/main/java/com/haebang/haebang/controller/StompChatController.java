package com.haebang.haebang.controller;

import com.haebang.haebang.dto.ChatMessageDTO;
import com.haebang.haebang.repository.ChatRoomRepository;
import com.haebang.haebang.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import javax.websocket.Session;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;


    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){

        chatRoomRepository.enterChatRoom(message.getRoomId());
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");

        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    @MessageMapping(value="/chat/exit")
    public void exit(ChatMessageDTO message){
        message.setMessage(message.getWriter() + "님이 채팅방을 종료하셨습니다.");

        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);

    }
}