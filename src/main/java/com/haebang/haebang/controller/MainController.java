package com.haebang.haebang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequiredArgsConstructor
public class MainController {

    @RequestMapping("/")
    public String main(Model model){

        return "main";
    }
}
