package com.pastebin.urlgenerator.controller;

import com.pastebin.urlgenerator.service.UrlGeneratorService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlGeneratorController {

    private final UrlGeneratorService urlGeneratorService;

    public UrlGeneratorController(UrlGeneratorService urlGeneratorService) {
        this.urlGeneratorService = urlGeneratorService;
    }


    @GetMapping("/gen")
    public String generateUrl() {
        return urlGeneratorService.getUrl();
    }
}
