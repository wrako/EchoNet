package com.pastebin.urlgenerator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlGeneratorService {

    @Autowired
    private RedisService redisService;

    public String getUrl() {
        redisService.ensureMinimumUrlsInCache();
        return redisService.getShortURL();
    }
}
