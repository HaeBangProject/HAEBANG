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
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final RedisService redisSampleService;
    private final MapService mapService;

    @RequestMapping("/")
    public String main(Model model) throws IOException {
        // 지역별 매물 검색 랭킹 5순위
        List<Map.Entry<String, Integer>> entryList = redisSampleService.rank_list();
        List<String> ranking = new ArrayList<>();
        List<Integer> ranking_score = new ArrayList<>();


        for(Map.Entry<String, Integer> entry : entryList){
            ranking.add(entry.getKey());
            ranking_score.add(entry.getValue());
        }

        List<String> rank_list = new ArrayList<>();
        rank_list.add(ranking.get(0));
        int rank_count=1;
        for(int i=1;i<ranking_score.size();i++){
            if(ranking_score.get(i)!=ranking_score.get(i-1)||i==ranking_score.size()-1){ //앞의 리스트와 점수가 다를때
                String str = String.join(",",rank_list);
                model.addAttribute("rank"+rank_count,str);
                rank_list=new ArrayList<>();
                rank_count+=1;
                rank_list.add(ranking.get(i));
            }
            else{
                rank_list.add(ranking.get(i));
            }
        }
        int rank_sggCd = mapService.rank_one(ranking.get(0));

        LocalDate seoul_now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        String last_month = String.valueOf(seoul_now.minusMonths(1));
        String[] time = last_month.split("-");
        List<MapDto> list = mapService.search_map(time[0],time[1], rank_sggCd, ranking.get(0)); //지난달 기준 랭킹1위 지역의 매물 정보
        model.addAttribute("list",list);
        model.addAttribute("rankTop1",ranking.get(0));
        return "main";
    }
}
