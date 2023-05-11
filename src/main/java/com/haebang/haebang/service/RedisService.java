package com.haebang.haebang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class RedisService {
    final StringRedisTemplate redisTemplate;

    public void ranking(String content){
        ZSetOperations<String,String> stringStringZSetOperations = redisTemplate.opsForZSet();

        double score = 0.0;

        try{
            // 검색을하면 해당검색어를 value에 저장하고, score에 1을 준다
            redisTemplate.opsForZSet().incrementScore("ranking",content,1);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        //score를 1씩 올려준다.
        redisTemplate.opsForZSet().incrementScore("ranking",content,score);

        Set<ZSetOperations.TypedTuple<String>> rankSet = stringStringZSetOperations.reverseRangeWithScores("ranking",0,9);
        System.out.println(rankSet);

    }

    public List<String> rank_list(){
        ZSetOperations<String,String> stringStringZSetOperations = redisTemplate.opsForZSet();
        Set<String> scoreRange = stringStringZSetOperations.reverseRange("ranking",0,9);
        return new ArrayList<>(Objects.requireNonNull(scoreRange));

    }
    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Redis key : " + key);
        System.out.println("Redis value : " + stringValueOperations.get(key));
    }

    public void setRedisStringValue(String key, String value) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        System.out.println("Redis key : " + key);
        System.out.println("Redis value : " + stringValueOperations.get(key));
    }
    public void setRedisStringValueExpire(String key, String value, Long duraionTime) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value, duraionTime);
        System.out.println("Redis key : " + key);
        System.out.println("Redis value : " + stringValueOperations.get(key));
    }

    public void deleteKey(String key){
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.getAndDelete(key);
        System.out.println("Delete Key : "+key);
    }
}