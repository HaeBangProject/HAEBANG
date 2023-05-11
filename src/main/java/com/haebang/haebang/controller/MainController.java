package com.haebang.haebang.controller;

import com.haebang.haebang.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    final private RedisService redisSampleService;

    @RequestMapping("/")
    public String main(Model model){
        // 지역별 매물 검색 랭킹 10순위
        List<String> ranking =redisSampleService.rank_list();
        model.addAttribute("rank",ranking);
        return "main";
    }
}
