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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @RequestMapping("/dp_detail/{dp_id}")
    public String dp_detail(Model model,@PathVariable String dp_id){
        long id = Long.parseLong(dp_id);
        System.out.println(dp_id);
        Item item = aptService.findItem2(id).orElseThrow();
        Apt apt = aptService.findByAptId(id).orElseThrow();
        model.addAttribute("item",item);
        model.addAttribute("apt",apt);
        return "detail";

    }

    @GetMapping("/item/write")
    public String write(){
        return "item/write";
    }

    @PostMapping("/error")
    public String error(){
        return "error";
    }
}
