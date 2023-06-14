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
            System.out.println(e.toString());
        }


        Set<ZSetOperations.TypedTuple<String>> rankSet = stringStringZSetOperations.reverseRangeWithScores("ranking",0,9);
        System.out.println(rankSet);

    }

    public List<String> rank_list(){
        ZSetOperations<String,String> stringStringZSetOperations = redisTemplate.opsForZSet();
        Long redis_size = redisTemplate.opsForZSet().size("ranking");
        Double test_score =null;
        Map<Double,Integer> score = new HashMap<>(); //스코어를 담기위한 리스트
        for(int i = 0; i < redis_size; i++) {
            Set<String> test = stringStringZSetOperations.reverseRange("ranking", i, i);
            for(String str : test) {
                System.out.println(str);
                test_score = stringStringZSetOperations.score("ranking", str);
                score.put(test_score,score.getOrDefault(test_score, 0)+1);

            }
        }
        System.out.println(score.get(0));
        int same_score=0;
        for(Double key : score.keySet()){
            if(score.get(key)>1){
                same_score += score.get(key) - 1;
            }
        }
            System.out.println(same_score);
            Set<String> scoreRange = stringStringZSetOperations.reverseRange("ranking",0,9+same_score);
            System.out.println(scoreRange);
        return new ArrayList<>(Objects.requireNonNull(scoreRange));

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