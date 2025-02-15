package com.pastebin.pasterbin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cache.text.ttl:1800}") // TTL для текста в секундах (по умолчанию 30 минут)
    private long textTTL;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(textTTL));
        System.out.println("saved in Cache " + key);
    }

    public String get(String key)
    {
        System.out.println("get from Cache" + key);
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
