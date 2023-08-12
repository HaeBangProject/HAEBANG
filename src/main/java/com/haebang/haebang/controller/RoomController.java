package com.haebang.haebang.controller;


import com.haebang.haebang.entity.ChatRoom;
import com.haebang.haebang.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "chat")
public class RoomController {
    private final ChatRoomRepository repository;

    //채팅방 목록 조회
    @RequestMapping(value = "rooms")
    public ModelAndView rooms(HttpServletRequest request){
        log.info("# All Chat Rooms");

        ModelAndView mv = new ModelAndView("chat/rooms");
        mv.addObject("list", repository.findAllRoom());
        return mv;

//        ModelAndView mv = new ModelAndView("alert");
//        mv.addObject("msg", "로그인을 해주세요.\n");
//        mv.addObject("url", "/");
//        return mv;
    }

    //채팅방 개설
    @PostMapping(value = "room")
    public String create(Model model,@RequestParam String name){

        log.info("# Create Chat Room , name: " + name);
        ChatRoom dto = repository.createChatRoom(name);
        model.addAttribute("room",dto);
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