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
        // connect 또는 send 상황이면 유효한 토큰인지 검증
        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
        }
        else if(accessor.getCommand() == StompCommand.SEND){
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
        }

        return message;
    }

}