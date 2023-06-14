package com.haebang.haebang.controller;

import com.haebang.haebang.dto.MapDto;
import com.haebang.haebang.service.MapService;
import com.haebang.haebang.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    final private RedisService redisSampleService;
    final private MapService mapService;

    @RequestMapping("/")
    public String main(Model model) throws IOException {
        // 지역별 매물 검색 랭킹 10순위
        List<String> ranking =redisSampleService.rank_list();
        model.addAttribute("rank",ranking);
        System.out.println(ranking);
        int rank_sggCd = mapService.rank_one(ranking.get(0));

        LocalDate seoul_now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        String last_month = String.valueOf(seoul_now.minusMonths(1));
        String[] time = last_month.split("-");
        List<MapDto> list = mapService.search_map(time[0],time[1], rank_sggCd, ranking.get(0)); //지난달 기준 랭킹1위 지역의 매물 정보
        model.addAttribute("list",list);
        return "main";
    }
}
