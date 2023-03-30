package com.haebang.haebang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MapController {

    @RequestMapping("/map")
    public String map(Model model){
        model.addAttribute("test","test");
        return "map";
    }


}
