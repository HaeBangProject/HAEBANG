package com.haebang.haebang.service;

import com.haebang.haebang.dto.MapDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EnableConfigurationProperties
@SpringBootTest(classes = MapService.class)
class MapServiceTest {

    @Value("${haebang.secret.key}")
    private String haebang_key;

    @Test
    void search_map() throws IOException {

        int local_code;//지역 코드
        String deal_ymd ; //계약년월

        //테스트코드를 위한 값 설정
        String year = "2022";
        String month="5";
        int sggCd=11350;
        String dong="상계동";

        local_code = sggCd;
        if (month.length()==1){
            month="0"+month;
        }
        deal_ymd = year +month;

        StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev?serviceKey="+haebang_key+"&pageNo=1&numOfRows=1000&LAWD_CD="+local_code+"&DEAL_YMD="+deal_ymd);
        System.out.println(urlBuilder);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/

        BufferedReader rd;
        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        JSONObject json = XML.toJSONObject(sb.toString());
        String jsonStr = json.toString();

        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONObject response = (JSONObject)jsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject)body.get("items");
        JSONArray item = (JSONArray) items.get("item");


        List<String> address = new ArrayList<String>();
        List<String> contract = new ArrayList<String>();
        List<String> apart = new ArrayList<String>();
        List<String> build = new ArrayList<String>();
        List<String> area = new ArrayList<String>();
        List<String> amount = new ArrayList<String>();

        String test_dong = dong; //동

        for(int i=0; i<item.length(); i++){
            jsonObject = item.getJSONObject(i);
            String value = jsonObject.getString("법정동"); //법정 주소
            if (value.equals(test_dong)) {
                String code_main = String.valueOf(jsonObject.getInt("법정동본번코드"));
                String code_sub = String.valueOf(jsonObject.getInt("법정동부번코드"));
                String contract_year = String.valueOf(jsonObject.getInt("년")); //계약 날짜
                String contract_month = String.valueOf(jsonObject.getInt("월"));
                String contract_day = String.valueOf(jsonObject.getInt("일"));
                String build_year = String.valueOf(jsonObject.getInt("건축년도"));
                String dp_area = String.valueOf(jsonObject.getInt("전용면적"));
                String dp_amount = String.valueOf(jsonObject.getString("거래금액"));
                String dp = String.valueOf(jsonObject.getString("아파트"));

                String test = "0";
                if (code_sub.equals(test)){
                    address.add(value+code_main);
                }
                else{
                    address.add(value+code_main+"-"+code_sub);
                }

                contract.add(contract_year+"년"+contract_month+"월"+contract_day+"일"); //계약한 날짜
                apart.add(dp+"아파트"); //아파트
                build.add(build_year); //건축년도
                area.add(dp_area+"㎡"); //전용 면적
                amount.add(dp_amount+"만원"); //거래금액
            }

        }

        MapDto mapDto = new MapDto();
        mapDto.setAddress(address);
        mapDto.setContract(contract);
        mapDto.setApart(apart);
        mapDto.setBuild(build);
        mapDto.setArea(area);
        mapDto.setAmount(amount);

    }


}
