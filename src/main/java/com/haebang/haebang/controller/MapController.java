package com.haebang.haebang.controller;

import com.haebang.haebang.service.MapService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.XML;
@Controller
@RequiredArgsConstructor
public class MapController {

    final private MapService mapservice;
    @Value("${haebang.secret.key}")
    private String haebang_key;

    @RequestMapping("/map")
    public String map(Model model,String year,String month,Integer sggCd,String dong) throws IOException {

        mapservice.map(year,month,sggCd,dong);



//
//        model.addAttribute("address1",address.get(0));
//        model.addAttribute("address2",address.get(1));
//        model.addAttribute("address3",address.get(2));
//        model.addAttribute("address4",address.get(3));
//        model.addAttribute("address5",address.get(4));
//
//        model.addAttribute("contract1",contract.get(0));
//        model.addAttribute("contract2",contract.get(1));
//        model.addAttribute("contract3",contract.get(2));
//        model.addAttribute("contract4",contract.get(3));
//        model.addAttribute("contract5",contract.get(4));
//
//        model.addAttribute("apart1",apart.get(0));
//        model.addAttribute("apart2",apart.get(1));
//        model.addAttribute("apart3",apart.get(2));
//        model.addAttribute("apart4",apart.get(3));
//        model.addAttribute("apart5",apart.get(4));
//
//        model.addAttribute("build1",build.get(0));
//        model.addAttribute("build2",build.get(1));
//        model.addAttribute("build3",build.get(2));
//        model.addAttribute("build4",build.get(3));
//        model.addAttribute("build5",build.get(4));
//
//        model.addAttribute("area1",area.get(0));
//        model.addAttribute("area2",area.get(1));
//        model.addAttribute("area3",area.get(2));
//        model.addAttribute("area4",area.get(3));
//        model.addAttribute("area5",area.get(4));
//
//        model.addAttribute("amount1",amount.get(0));
//        model.addAttribute("amount2",amount.get(1));
//        model.addAttribute("amount3",amount.get(2));
//        model.addAttribute("amount4",amount.get(3));
//        model.addAttribute("amount5",amount.get(4));





        return "map";
    }






}
