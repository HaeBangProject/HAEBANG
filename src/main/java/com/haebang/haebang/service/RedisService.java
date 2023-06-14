package com.haebang.haebang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveZSetCommands;
import org.springframework.data.redis.core.RedisOperations;
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
    private final StringRedisTemplate redisTemplate;

    public void ranking(String content) {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();


        try {
            redisTemplate.opsForZSet().incrementScore("ranking", content, 1);


        } catch (Exception e) {
            System.out.println(e.toString());
        }
        Long redis_size = redisTemplate.opsForZSet().size("ranking");

        Double content_score = stringStringZSetOperations.score("ranking", content); //해당 value값의 score
        Double test_score = null;
        Double test_score2 = null;
        Double test_score3 = null;




        for (int i = 0; i < redis_size; i++) {
            Set<String> test = stringStringZSetOperations.reverseRange("ranking", i, i);
            for (String str : test) {
                System.out.println(str);
                test_score = stringStringZSetOperations.score("ranking", str);
                System.out.println(test_score);
                if (Objects.equals(test_score, content_score)&&(!str.equals(content))) { // 같은 score 값을 가진 value들이 존재할때
                    stringStringZSetOperations.add("ranking", "("+str + "," + content+")", test_score);
                    stringStringZSetOperations.remove("ranking", str);
                    stringStringZSetOperations.remove("ranking", content);
                    stringStringZSetOperations.add("ranking2",str,test_score);
                    stringStringZSetOperations.add("ranking2",content,test_score);

                }
            }

        }

        Long rank2_size = redisTemplate.opsForZSet().size("ranking2");

        for(int j = 0; j <rank2_size; j++){ //같은 score값들로 존재하는 value값들 중 score를 올리려할때
            Set<String> test2 = stringStringZSetOperations.reverseRange("ranking2", j, j);
            for (String str2 : test2) {
                System.out.println(str2);
                test_score2 = stringStringZSetOperations.score("ranking2", str2);

                if (Objects.equals(content, str2)&&(!Objects.equals(test_score2, content_score))) { // ranking2과 ranking에서 같은 value 찾기
                    redisTemplate.opsForZSet().incrementScore("ranking2", str2, 1); //score올려준다.

                    stringStringZSetOperations.add("ranking",str2,test_score2);
                    stringStringZSetOperations.removeRangeByScore("ranking", test_score2,test_score2);

                    for(String str3:test2){ // 자신제외  ranking2에 있는 content_score-1와 score가 같은 value값들 집어넣기
                        test_score3 = stringStringZSetOperations.score("ranking2", str3);
                        System.out.println(test_score3);
                        if(!content.equals(str3)&&(Objects.equals(test_score3, content_score-1))){
                            stringStringZSetOperations.add("ranking",str3,test_score2);

                        }
                    }

                    stringStringZSetOperations.removeRangeByScore("ranking2",test_score2,test_score2);

                }

            }
        }




        Set<ZSetOperations.TypedTuple<String>> rankSet = stringStringZSetOperations.reverseRangeWithScores("ranking", 0, 9);
        System.out.println(rankSet);

        Set<ZSetOperations.TypedTuple<String>> rankSet2 = stringStringZSetOperations.reverseRangeWithScores("ranking2", 0, 9);
        System.out.println(rankSet2);

    }



    public List<String> rank_list(){
        ZSetOperations<String,String> stringStringZSetOperations = redisTemplate.opsForZSet();

        Set<String> scoreRange = stringStringZSetOperations.reverseRange("ranking",0,9);
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