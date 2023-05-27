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

    MapDto mapDto;
    @RequestMapping("/map")
    public String map(Model model,String year,String month,Integer sggCd,String dong) throws IOException {

        mapDto=mapservice.search_map(year,month,sggCd,dong);

        List <String> address = new ArrayList<>();
        List <String> contract = new ArrayList<>();
        List <String> apart = new ArrayList<>();
        List <String> build = new ArrayList<>();
        List <String> area = new ArrayList<>();
        List <String> amount = new ArrayList<>();



        address=mapDto.getAddress();
        contract=mapDto.getContract();
        apart=mapDto.getApart();
        build=mapDto.getBuild();
        area=mapDto.getArea();
        amount=mapDto.getAmount();
        model.addAttribute("mapDto",mapDto);
        System.out.println(mapDto);

        if (build.isEmpty()){
            model.addAttribute("msg", "매물이 존재하지 않습니다.\n 검색어를 다시 입력해주세요.");
            model.addAttribute("url", "/");
            return "alert";
        }

        model.addAttribute("address",address);
        model.addAttribute("contract",contract);
        model.addAttribute("apart",apart);
        model.addAttribute("build",build);
        model.addAttribute("area",area);
        model.addAttribute("amount",amount);

        if(!Objects.equals(dong, null)){
           redisSampleService.ranking(dong);
        }

        return "map";
    }


}
