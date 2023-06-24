package com.haebang.haebang.controller;

import com.haebang.haebang.dto.MapDto;
import com.haebang.haebang.service.MapService;
import com.haebang.haebang.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MapController {
    final private MapService mapservice;
    final private RedisService redisSampleService;

    @RequestMapping("/map")
    public String map(Model model,String year,String month,Integer sggCd,String dong) throws IOException {

        List<MapDto> list = mapservice.search_map(year,month,sggCd,dong);


        System.out.println(list);
        model.addAttribute("list",list);


        if (list.isEmpty()){
            model.addAttribute("msg", "매물이 존재하지 않습니다.\n 검색어를 다시 입력해주세요.");
            model.addAttribute("url", "/");
            return "alert";
        }



        if(!Objects.equals(dong, null)){
            redisSampleService.ranking(dong);
        }
        model.addAttribute("year",year);
        model.addAttribute("month",month);
        model.addAttribute("dong",dong);

        return "map";
    }


}
