package com.haebang.haebang.utils;

import com.haebang.haebang.exception.CustomException;
import com.haebang.haebang.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionEventListener;
import org.hibernate.cache.spi.support.CacheUtils;
import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final ChatRoomRepository chatRoomRepository;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        JSONObject jsonObject = new JSONObject(message.getPayload());

        if(accessor.getCommand() == StompCommand.CONNECT) {// 세션과 username 정보 연결만 함
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
            chatRoomRepository.getSessionIdUsername()
                    .put(accessor.getSessionId(),
                            jwtProvider.getUsername(accessor.getFirstNativeHeader("token")));
        }
        if(accessor.getCommand() == StompCommand.SEND){// 이후 모든 요청은 저장된 정보에서 user가져옴
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
            System.out.println("session id : "+accessor.getSessionId());

            // 세션id 저장돼있으면 가져오기
            if(chatRoomRepository.getSessionIdUsername().containsKey(accessor.getSessionId())) {
                accessor.setNativeHeader("username", chatRoomRepository.getSessionIdUsername().get(accessor.getSessionId()));
            }else new Exception("잘못된 세션 접근 요청");
        }

        return message;
    }

}