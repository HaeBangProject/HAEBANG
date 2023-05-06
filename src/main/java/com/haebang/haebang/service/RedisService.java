package com.haebang.haebang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class RedisService {



    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    //검색시마다 key의 value값 카운트
    public void ranking(String content){
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        if(!Objects.equals(stringValueOperations.get(content), null)) {
            int score = Integer.parseInt(Objects.requireNonNull(stringValueOperations.get(content)))+1;
            stringValueOperations.set(content, String.valueOf(score));

        }
        else{
            stringValueOperations.set(content, "1");

        }
        System.out.println("Redis key : " + content);
        System.out.println("Redis value : " + stringValueOperations.get(content));
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



}