package com.haebang.haebang.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class PageController {

    @GetMapping("mypage")
    public String mypage(){
        return "mypage";
    }
    @GetMapping("/memberLogin")
    public String memberlogin(){
        return "memberLogin";
    }
    @GetMapping("/memberJoin")
    public String memberJoin(){
        return "memberJoin";
    }
}
