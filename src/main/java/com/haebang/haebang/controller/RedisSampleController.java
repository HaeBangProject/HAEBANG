package com.haebang.haebang.controller;

import com.haebang.haebang.service.CustomRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RedisSampleController {

    final private CustomRedisService redisSampleService;

    @PostMapping(value = "/getRedisStringValue")
    public void getRedisStringValue(String key) {
        redisSampleService.getStringValue(key);
    }


}