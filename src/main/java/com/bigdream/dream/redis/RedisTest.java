package com.bigdream.dream.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-13 14:49
 **/
@RequestMapping("redis")
@RestController
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/set", method = RequestMethod.GET)
    @ResponseBody
    public boolean set(@RequestParam String key,@RequestParam Object value) {
        long i =18000;
        boolean rsult = redisUtils.set(key, value,i, TimeUnit.MILLISECONDS);
//        boolean rsult = redisUtils.set(key, value);
        System.out.println(rsult);
        return rsult;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam String key) {
        String value = (String) redisUtils.get(key);
        System.out.println(value);
        return value;
    }


}
