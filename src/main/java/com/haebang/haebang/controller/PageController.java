package com.haebang.haebang.controller;

import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.service.AptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

@RequiredArgsConstructor
@Controller
public class PageController {
    final AptService aptService;
    @GetMapping("mypage")
    public String mypage(){
        return "mypage";
    }
    @GetMapping("/memberLogin")
    public String memberLogin(){
        return "memberLogin";
    }

    @RequestMapping("/apt")
    public String showApt(Model model){

        List<Apt> list = aptService.findAllApt();
        model.addAttribute("Apt",list);

        return "showApt";
    }
    @RequestMapping("/item/detail/{road_address}")
    public String dp_detail(Model model,@PathVariable("road_address") String road_address){
        System.out.println(road_address);
        Apt apt = aptService.findAptByRoadAddress(road_address);
        model.addAttribute("apt", apt);
        return "item/detail";
    }

    @GetMapping("item/write")
    public String write(Model model){
        model.addAttribute("item_id", 0);
        return "item/write";
    }
    @GetMapping("item/edit/{item_id}")
    public String edit(Model model, @PathVariable("item_id") Long id){
        System.out.println("매물 작성 페이지 로딩 : "+id);
        model.addAttribute("item_id", id);
        return "item/write";
    }

    @PostMapping("/error")
    public String error(){
        return "error";
    }
}
