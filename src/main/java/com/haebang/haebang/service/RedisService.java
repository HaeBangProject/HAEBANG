package com.haebang.haebang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void ranking(String content){
        ZSetOperations<String,String> stringStringZSetOperations = redisTemplate.opsForZSet();

        double score = 0.0;

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
        // score = {8.0=1, 2.0=3, 1.0=10, 5.0=1, 3.0=2}
        int same_score=0;
        for(Double key : score.keySet()){
            if(score.get(key)>1){ //같은 score를 가진 데이터가 2개이상일때,
                same_score += score.get(key) - 1;
            }
        }
        Set<String> scoreRange = stringStringZSetOperations.reverseRange("ranking",0,4+same_score);
        // same_score = 12
        // scoreRange = [논현동, 상계동, 방학동, 길동, 창동, 쌍문동, 신림동, 수유동,
        // 석촌동, 삼성동, 번동, 둔촌동, 독산동, 대치동, 노량진동, 공릉동, 고덕동]

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
        // entryList = [논현동=8, 상계동=5, 길동=3, 방학동=3, 창동=2, 신림동=2, 쌍문동=2, 수유동=1, 석촌동=1, 공릉동=1, 번동=1, 삼성동=1, 노량진동=1, 독산동=1, 대치동=1, 고덕동=1, 둔촌동=1]

        return entryList;

    }


    public void setRedisStringValueExpire(String key, String value, Long duraionTime) {
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.set(key, value, duraionTime);
        System.out.println("Redis key : " + key);
        System.out.println("Redis value : " + stringValueOperations.get(key));
    }

    public void deleteKey(String key){
        ValueOperations<String, String> stringValueOperations = redisTemplate.opsForValue();
        stringValueOperations.getAndDelete(key);
        System.out.println("Delete Key : "+key);
    }
}