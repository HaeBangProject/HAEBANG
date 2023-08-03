package com.haebang.haebang.service;

import com.haebang.haebang.dto.MapDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MapService {
    @Value("${haebang.secret.key}")
    private String haebang_key;

    public List<MapDto> search_map(String year, String month, Integer sggCd, String dong) throws IOException {


        if(sggCd==0||dong=="읍/면/동"){
            List<MapDto> list = new ArrayList<>();

            return list;
        }

        int local_code;//지역 코드
        String deal_ymd ; //계약년월

        local_code = sggCd;
        if (month.length()==1){
            month="0"+month;
        }
        deal_ymd = year+month;

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
        List<String> floor = new ArrayList<String>();


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
            String dp_floor = String.valueOf(jsonObject.getInt("층"));
            dp_amount = dp_amount.replaceAll(",", "");
            int x = Integer.parseInt(dp_amount);
            double y = Math.round(x /100) / 100.0;

            String test = "0";
            if (code_sub.equals(test)){
                address.add(value+code_main);
            }
            else{
                address.add(value+code_main+"-"+code_sub);
            }
            contract_year=contract_year.substring(2);
            contract.add(contract_month+"/"+contract_day); //계약한 날짜
            apart.add(dp+"아파트"); //아파트
            build.add(build_year); //건축년도
            area.add(dp_area+"㎡"); //전용 면적
            amount.add(String.valueOf(y)+"억");//거래금액
            floor.add(dp_floor+"층");
            }

        }

        List<MapDto> list = new ArrayList<>();
        for(int i=0;i<address.size();i++){
            list.add(new MapDto(address.get(i),contract.get(i),apart.get(i),build.get(i),area.get(i),floor.get(i),amount.get(i)));

        }
        List<Integer> test=new ArrayList<>();
        //동일한 좌표값들을 한 마커에 띄우기 위한 for문
        for(int i=0;i<address.size();i++) {
            for(int j=i+1;j<address.size();j++) {
                if(Objects.equals(address.get(i), address.get(j))){
                    list.get(j).setContract(list.get(j).getContract()+","+contract.get(i));
                    list.get(j).setApart(list.get(j).getApart()+","+apart.get(i));
                    list.get(j).setBuild(list.get(j).getBuild()+","+build.get(i));
                    list.get(j).setArea(list.get(j).getArea()+","+area.get(i));
                    list.get(j).setFloor(list.get(j).getFloor()+","+floor.get(i));
                    list.get(j).setAmount(list.get(j).getAmount()+","+amount.get(i));
                    test.add(i);


                }
            }
        }

            return list;
    }

    public Integer rank_one(String rank_one){
        List<String> good_a = List.of("개포동", "논현동", "대치동", "도곡동","삼성동","세곡동","신사동","역삼동","일원동","청담동");
        List<String> good_b = List.of("강일동", "고덕동", "길동", "둔촌동","성내동","암사동","천호동");
        List<String> good_c = List.of("미아동", "번동", "수유동", "우이동");
        List<String> good_d = List.of("가양동","개화동","공항동","과해동","내발산동","등촌동","마곡동","방화동","염창동","오곡동","오쇠동","외발산동","화곡동");
        List<String> good_e = List.of("남현동","봉천동","신림동");
        List<String> good_f = List.of("광장동","구의동","군자동","능동","자양동","중곡동","화양동");
        List<String> good_g = List.of("가리봉동","개봉동","고척동","구로동","궁동","신도림동","오류동","온수동","천왕동","항동");
        List<String> good_h = List.of("가산동","독산동","시흥동");
        List<String> good_i = List.of("공릉동","상계동","월계동","중계동","하계동");
        List<String> good_j = List.of("도봉동","방학동","쌍문동","창동");
        List<String> good_k = List.of("답십리동","신설동","용두동","이문동","장안동","전농동","제기동","청량리동","회기동","휘경동");
        List<String> good_l = List.of("노량진동","대방동","동작동","본동","사당동","상도1동","상도동","신대방동","흑석동");
        List<String> good_m = List.of("공덕동","구수동","노고산동","당인동","대흥동","도화동","동교동","마포동","망원동","상수동","상암동","서교동","성산동","신공덕동","신수동","신정동","신수동","신정동","아현동","연남동","염리동","용강동","중동","창전동","토정동","하중동","합정동","현석동");
        List<String> good_n = List.of("남가좌동","냉천동","대신동","대현동","미근동","봉원동","북가좌동","북아현동","신촌동","연희동","영천동","옥천동","창천동","천연동","충정로2가","충정로3가","합동","현저동","홍은동","홍제동");
        List<String> good_o = List.of("내곡동","반포동","방배동","서초동","신원동","양재동","염곡동","우면동","원지동","잠원동");
        List<String> good_p = List.of("금호동1가","금호동2가","금호동3가","금호동4가","도선동","마장동","사근동","상왕십리동","성수동1가","성수동2가","송정동","옥수동","용답동","응봉동","하왕십리동","행당동","홍익동");
        List<String> good_q = List.of("길음동","돈암동","동선동1가","동선동2가","동선동3가","동산동4가","동선동5가","동소문동1가","동소문동2가","동소문동3가","동소문동4가","동소문동5가","동소문동6가","동소문동7가","보문동1가","보문동2가","보문동3가","보문동4가","보문동5가","보문동6가","보문동7가","삼선동1가","삼선동1가","삼선동2가","삼선동3가","삼선동4가","삼선동5가","상월곡동","석관동","성북동","성북동1가","안암동1가","안암동2가","안암동3가","안암동4가","안암동5가","장위동","정릉동","종암동","하월곡동");
        List<String> good_r = List.of("가락동","거여동","마천동","문정동","석촌동","송파동","신천동","오금동","잠실동","장지동","풍납동");
        List<String> good_s = List.of("목동","신월동","신정동");
        List<String> good_t = List.of("당산동","당산동1가","당산동2가","당산동3가","당산동4가","당산동5가","당산동6가","대림동","도림동","문래동1가","문래동2가","문래동3가","문래동4가","문래동5가","문래동6가","신길동","양평동","양평동1가","양평동2가","양평동3가","양평동4가","양평동5가","양평동6가","양화동","여의도동","영등포동","영등포동1가","영등포동2가","영등포동3가","영등포동4가","영등포동5가","영등포동6가","영등포동7가","영등포동8가");
        List<String> good_u = List.of("갈월동","남영동","도원동","동빙고동","동자동","문배동","보광동","산천동","서계동","서빙고동","신계동","신창동","용문동","용산동1가","용산동2가","용산동3가","용산동4가","용산동5가","용산동6가","원효로1가","원효로1가","원효로2가","원효로3가","원효로4가","이촌동","이태원동","주성동","청암동","청파동1가","청파동2가","청파동3가","한강로1가","한강로2가","한강로3가","한남동","효창동","후암동");
        List<String> good_v = List.of("갈현동","구산동","녹번동","대조동","불광동","수색동","신사동","역촌동","응암동","증산동","진관동");
        List<String> good_w = List.of("가회동","견지동","경운동","계동","공평동","관수동","관철동","관훈동","교남동","교북동","구기동","궁정동","권농동","낙원동","내수동","내자동","누상동","누하동","당주동","도렴동","돈의동","동숭동","명륜1가","명륜2가","명륜3가","명륜4가","묘동","무악동","봉익동","부암동","사간동","사직동","삼청동","서린동","세종로","소격동","송월동","송현동","수송동","숭인동","신교동","신문로1가","신문로2가","신영동","안국동","연건동","연지동","예지동","옥인동","와룡동","운니동","원남동","원서동","이화동","익선동","인사동",
                "인의동","장사동","재동","적선동","종로1가","종로2가","종로3가","종로4가","종로5가","종로6가","중학동","창성동","창신동","청운동","청진동","체부동","충신동","통의동","통인동","팔판동","평동","평창동","필운동","행촌동","혜화동","홍지동","홍파동","화동","효자동","효제동","훈정동");
        List<String> good_x = List.of("광희동1가","광희동2가","남대문로1가","남대문로2가","남대문로3가","남대문로4가","남대문로5가","남산동1가","남산동2가","남산동3가","남창동","남학동","다동","만리동1가","만리동2가","명동1가","명동2가","무교동","무학동","묵정동","방산동","봉래동1가","봉래동2가","북창동","산림동","삼각동","서수문동","소곡동","수표동","수하동","순화동","신당동","쌍림동","예관동","예장동","오장동","을지로1가","을지로2가","을지로3가","을지로4가","을지로5가","을지로6가","을지로7가","의주로1가","의주로2가","인현동1가","인현동2가","입정동","장교동",
                "장충동1가","장충동2가","저동1가","저동2가","정동","주교동","주자동","중림동","초동","충무로1가","충무로2가","충무로3가","충무로4가","충무로5가","태평로1가","태평로2가","필동1가","필동2가","필동3가","황학동","회현동1가","회현동2가","회현동3가","흥인동");
        List<String> good_y = List.of("망우동","면목동","묵동","상봉동","신내동","중화동");
        List<Integer> sggCd = List.of(11680,11740,11305,11500,11620,11215,11530,11545,11350,11320,11230,
                11590,11440,11650,11200,11290,11710,11470,11560,11170,11380,11110,11140,11260);
        for(int i=0;i<25;i++){
            List<List<String>> good_list = List.of(good_a, good_b, good_c, good_d,good_e,good_f,good_g,good_h,good_i,good_j,
                    good_k, good_l, good_m, good_n,good_o,good_p,good_q,good_r,good_x,good_s,good_t,
                    good_u, good_v, good_w, good_x,good_y);

            for(int j=0;j<good_list.get(i).size();j++){
                if (rank_one.equals(good_list.get(i).get(j))){
                    return sggCd.get(i);
                }
            }
        }


        return 0;
    }

}
