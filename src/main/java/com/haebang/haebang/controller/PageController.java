package com.haebang.haebang.controller;

import com.haebang.haebang.entity.Apt;
import com.haebang.haebang.entity.Item;
import com.haebang.haebang.repository.ItemRepository;
import com.haebang.haebang.service.AptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

@RequiredArgsConstructor
@Controller
public class PageController {
    final AptService aptService;
    final ItemRepository itemRepository;

    @GetMapping("mypage")
    public String mypage(HttpServletRequest request,Model model){
        if(request.getCookies()!=null) {// 로그인 유무
            return "mypage";
        }
        model.addAttribute("msg", "로그인을 해주세요.\n");
        model.addAttribute("url", "/");
        return "alert";
    }
    @GetMapping("mypage/items")
    public String myItems(){return "mypage/items";}
    @GetMapping("mypage/bookmarked")
    public String bookmarked(){return "mypage/bookmarked";}

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
        model.addAttribute("road_address", road_address);
        return "item/detail";
    }

    @GetMapping("item/write")
    public String write(Model model){

        model.addAttribute("item_id", 0);
        return "item/write";
    }
    @GetMapping("item/edit/{item_id}")
    public ModelAndView edit(@PathVariable("item_id") Long id){
        System.out.println("매물 작성 페이지 로딩 : "+id);
        ModelAndView mv = new ModelAndView("item/write");
        mv.addObject("item_id", id);
        return mv;
    }

    @PostMapping("/error")
    public String error(){
        return "error";
    }
}
