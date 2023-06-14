package com.haebang.haebang.controller;

import com.haebang.haebang.dto.ChatRoomDTO;
import com.haebang.haebang.dto.MemberReqDto;
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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "chat")
public class RoomController {
    private final ChatRoomRepository repository;
    private final MemberService memberService;
    MemberReqDto memberReqDto;

    //채팅방 목록 조회
    @RequestMapping(value = "rooms")
    public ModelAndView rooms(HttpServletRequest request){
        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");
        for(Cookie cookie : request.getCookies()) {
            if(Objects.equals(cookie.getName(), "username")) {
                System.out.println(cookie.getValue());
                mv.addObject("username",cookie.getValue());
            }
        }
            mv.addObject("list", repository.findAllRooms());
        return mv;
    }

    //채팅방 개설

    @PostMapping(value = "room")
    public String create(Model model,@RequestParam String name, @NotNull RedirectAttributes rttr){

        log.info("# Create Chat Room , name: " + name);
        ChatRoomDTO dto = repository.createChatRoomDTO(name);
        //rttr.addFlashAttribute("roomName", dto);
        model.addAttribute("room",dto);
        return "chat/room";
//        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("room")
    public void getRoom(String roomId, Model model/*, @AuthenticationPrincipal MemberReqDto memberReqDto*/){

        log.info("# get Chat Room, roomID : " + roomId);

        model.addAttribute("room", repository.findRoomById(roomId));
//        model.addAttribute("member", memberReqDto);

    }
}