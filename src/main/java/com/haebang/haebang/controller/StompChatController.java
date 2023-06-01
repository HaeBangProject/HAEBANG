package com.haebang.haebang.controller;

import com.haebang.haebang.dto.ChatMessageDTO;
import com.haebang.haebang.repository.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message, SimpMessageHeaderAccessor  headerAccessor){
        //String username = chatRoomRepository.getSessionIdUsername().get(session.getId());
        //System.out.println(session.getId()+" "+username);
//        message.setWriter(username);
        String sessionId = headerAccessor.getSessionId();
        log.info("session id"+sessionId);
//        String sessionId = session.getMessage().getHeaders().get("simpleSessionId").toString();
        String username = chatRoomRepository.getSessionIdUsername().get(sessionId);
        message.setWriter(username);
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message, SimpMessageHeaderAccessor headerAccessor){
        String sessionId = headerAccessor.getSessionId();
        message.setWriter(chatRoomRepository.getSessionIdUsername().get(sessionId));
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
