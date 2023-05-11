package com.haebang.haebang.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
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

}