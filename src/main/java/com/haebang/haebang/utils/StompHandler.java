package com.haebang.haebang.utils;

import com.haebang.haebang.dto.ChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(accessor.getCommand() == StompCommand.CONNECT) {
            if(!jwtProvider.validateToken(accessor.getFirstNativeHeader("token")))
                throw new AccessDeniedException("");
            String username = jwtProvider.getUsername(accessor.getFirstNativeHeader("token"));
            accessor.setHeader("username",username);
            accessor.setMessage(username+"님이 채팅방에 참여하였습니다.");
            System.out.println("connect user "+accessor.getFirstNativeHeader("username"));
        }

        if(accessor.getCommand() == StompCommand.SEND){
            accessor.setMessage(accessor.getLogin()+" : "+accessor.getMessage());
        }

        return message;
    }
}