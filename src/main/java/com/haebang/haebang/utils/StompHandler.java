package com.haebang.haebang.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.CONNECT) {// 세션과 username 정보 연결만 함
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
        }
        else if(accessor.getCommand() == StompCommand.SEND){// 이후 모든 요청은 저장된 정보에서 user가져옴
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
        }

        return message;
    }

}