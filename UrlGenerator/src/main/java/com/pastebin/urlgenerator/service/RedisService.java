package com.pastebin.urlgenerator.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.slf4j.LoggerFactory;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashGenService urlGeneratorService;

    @Value("${cache.url.list.key:short_urls}")
    private String redisUrlListKey;

    @Value("${cache.url.list.minCount:10}")
    private int minUrlCount;

    public RedisService(RedisTemplate<String, Object> redisTemplate, HashGenService urlGeneratorService) {
        this.redisTemplate = redisTemplate;
        this.urlGeneratorService = urlGeneratorService;
    }

    @PostConstruct
    public void initialize() {
        ensureMinimumUrlsInCache();
    }

    public String getShortURL() {
        return (String) redisTemplate.opsForList().leftPop(redisUrlListKey);
    }

    @Async("asyncExecutor")
    public void ensureMinimumUrlsInCache() {

        Long currentCount = redisTemplate.opsForList().size(redisUrlListKey);

        if (currentCount == null || currentCount < minUrlCount) {
            for (int i = 0; i < (minUrlCount - currentCount); i++) {
                String generatedUrl = urlGeneratorService.generateUniqueUrl();
                redisTemplate.opsForList().rightPush(redisUrlListKey, generatedUrl);
            }
        }

        }

}
