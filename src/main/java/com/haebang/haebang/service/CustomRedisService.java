package com.haebang.haebang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomRedisService {

    final private StringRedisTemplate stringRedisTemplate;

    public void setStringKey(String key, String value){
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value);
        System.out.println(key +" : "+value);
    }
    public void setStringKey(String key, String value, Duration duration){
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(key, value, duration);
        System.out.println(key +" : "+value +" duration : "+duration.toMinutes());
    }

    public String getStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Redis key : " + key);
        return stringValueOperations.get(key);
    }

    public void deleteStringKey(String key){
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.getAndDelete(key);
    }
}