package com.haebang.haebang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;


@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void ranking(String content){
        try{
        // 검색을하면 해당검색어(content)를 value에 저장하고, score에 1을 주어 score를 올려준다.
            redisTemplate.opsForZSet().incrementScore("ranking",content,1);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public List<Map.Entry<String, Integer>> rank_list(){
        ZSetOperations<String,String> stringStringZSetOperations = redisTemplate.opsForZSet();
        Long redis_size = redisTemplate.opsForZSet().size("ranking");
        Double test_score =null;
        Map<Double,Integer> score = new HashMap<>(); //스코어를 담기위한 Map
        if (redis_size==0){
            redisTemplate.opsForZSet().incrementScore("ranking","상계동",0);
        }
        if(redis_size==2){ //첫 검색어가 들어왔을때,기존에 설정했던 key에서 상계동을 지우기
            redisTemplate.opsForZSet().remove("ranking","상계동");
        }
        for(int i = 0; i < redis_size; i++) {
            Set<String> test = stringStringZSetOperations.reverseRange("ranking", i, i);

            for(String str : test) {
                test_score = stringStringZSetOperations.score("ranking", str);
                score.put(test_score,score.getOrDefault(test_score, 0)+1);

            }
        }
        List<Map.Entry<Double, Integer>> sortedList = new ArrayList<>(score.entrySet());
        // key인 double값을 기준으로 내림차순 정렬
        sortedList.sort(Collections.reverseOrder(Map.Entry.comparingByKey()));

        int same_score=0;
        //score가 top5 안에 들어가고 ,같은 score를 가진 데이터가 2개이상일때
        for (int i = 0; i <= 4 && i < sortedList.size(); i++) {
            Map.Entry<Double, Integer> entry = sortedList.get(i);
            if(entry.getValue()>1) {
                same_score += entry.getValue() - 1;
                System.out.println("Score: " + entry.getKey() + ", Value: " + entry.getValue());
            }
        }
        
        // 공동 순위 처리한 top5 랭킹
        Set<String> scoreRange = stringStringZSetOperations.reverseRange("ranking",0,4+same_score);

        List<Integer> scoreRange_score = new ArrayList<>();

        for(String str : scoreRange) {
            int i;
            double d = stringStringZSetOperations.score("ranking", str);
            i = Integer.parseInt(String.valueOf(Math.round(d))); //double값 int로 변환
            scoreRange_score.add(i);
        }

        List<String> targetList = new ArrayList<>(scoreRange);
        Map<String, Integer> rank = new HashMap<>();
        for(int j=0;j<scoreRange.size();j++){
            rank.put(targetList.get(j),scoreRange_score.get(j));
        }

        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(rank.entrySet());
        entryList.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        System.out.println(entryList);

        return entryList;

    }


    public void setStringValueExpire(String key, String value, Duration duraionTime) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set(key, value, duraionTime);
    }
    public String getStringValue(String key){
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        return stringValueOperations.get(key);
    }

    public void deleteKey(String key){
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();

        System.out.println("Delete Key : "+stringValueOperations.getAndDelete(key));
    }
}