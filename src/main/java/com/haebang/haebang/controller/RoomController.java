package com.haebang.haebang.controller;

import com.haebang.haebang.dto.ChatRoomDTO;
import com.haebang.haebang.repository.ChatRoomRepository;
import com.haebang.haebang.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseCookie;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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
//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies) {
//            if(cookie.getUsername()==null){
//
//                ModelAndView mv = new ModelAndView("alert");
//                mv.addObject("msg", "로그인을 해주세요.\n");
//                mv.addObject("url", "/");
//                return mv;
//            }
//        }

        ModelAndView mv = new ModelAndView("chat/rooms");

        mv.addObject("list", repository.findAllRooms());

        return mv;
    }

    //채팅방 개설
    @PostMapping(value = "room")
    public String create(@RequestParam String name, @NotNull RedirectAttributes rttr){

        log.info("# Create Chat Room , name: " + name);
        ChatRoomDTO dto = repository.createChatRoomDTO(name);
        rttr.addFlashAttribute("roomName", dto);
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("room")
    public void getRoom(String roomId, Model model/*, @AuthenticationPrincipal MemberReqDto memberReqDto*/){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", repository.findRoomById(roomId));
//        model.addAttribute("member", memberReqDto);

    }
}