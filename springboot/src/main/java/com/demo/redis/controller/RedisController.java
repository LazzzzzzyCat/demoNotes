package com.demo.redis.controller;

import com.demo.redis.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis哨兵测试
 * @author huwj
 * @date 2020/8/8 21:29
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Scheduled(cron = "0/5 * * * * *")
    public void redisSentinel(){
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String value = stringStringValueOperations.get("v1");
        System.out.println(value);
    }

    @PostMapping
    public void test(@ModelAttribute("hahaModel") Person person){

    }

    private void hahaModel(Person person){

    }
}
