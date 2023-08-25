package com.haebang.haebang.controller;


import com.haebang.haebang.model.ChatRoom;
import com.haebang.haebang.repository.ChatRoomRepository;
import com.haebang.haebang.utils.JwtProvider;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "chat")
public class RoomController {
    private final ChatRoomRepository repository;
    private final JwtProvider jwtProvider;

    @RequestMapping(value = "rooms")
    public ModelAndView rooms(){
        return new ModelAndView("chat/rooms");
    }

    //채팅방 목록 조회
    @PostMapping(value = "rooms")
    @ResponseBody
    public ResponseEntity rooms(HttpServletRequest request){
        log.info("# All Chat Rooms");
        try{
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = authorization!=null ?authorization.split(" ")[1] : null;
            if (token != null && token.length() > 0 && jwtProvider.validateToken(token)) {
                // 유효한 토큰 일때

                String type = jwtProvider.getTokenType(token);
                if (type.equals("ATK")) {// 엑세스 토큰 일떄
                    log.info("유효한 엑세스 토큰요청");
                    if ( jwtProvider.getValueFromToken(token) == null) {
                        // 로그아웃되지 않은 ATK라면 정상 작동 하도록
                        Long restTime = jwtProvider.getExpireTime(token) - new Date(System.currentTimeMillis()).getTime();
                        if(restTime < 10*1000*60L){// 10분 이내로 남았으면 재발급 하도록 하기 위해
                            log.info("10분 이내로 남음");
                            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                        }
                    }
                }
            }
        }catch (JwtException e){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(repository.findAllRoom(), HttpStatus.OK);
    }

    //채팅방 개설
    @PostMapping(value = "room")
    public String create(Model model,@RequestParam String name,HttpServletRequest request){
        for(Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("username")) {
                String username = cookie.getValue(); // Example: Extract substring

                log.info("# Create Chat Room , name: " + name);
                ChatRoom dto = repository.createChatRoom(name, username);

                model.addAttribute("room", dto);
            }
        }
        return "chat/room";
    }

    //채팅방 조회
    @GetMapping("room")
    public void getRoom(String roomId, Model model/*, @AuthenticationPrincipal MemberReqDto memberReqDto*/){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", repository.findRoomById(roomId));
//        model.addAttribute("member", memberReqDto);

    }

    //채팅방 종료
    @PostMapping("exit")
    public String exitRoom(String roomId,Model model){
        repository.deleteRoomById(roomId);
        model.addAttribute("msg", "채팅이 종료되었습니다.");
        model.addAttribute("url", "/");
        return "alert";
    }
}