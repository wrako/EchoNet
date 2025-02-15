package com.pastebin.pasterbin.controller;

import com.pastebin.pasterbin.dto.PasteRequest;
import com.pastebin.pasterbin.entity.ContentType;
import com.pastebin.pasterbin.entity.Paste;
import com.pastebin.pasterbin.repo.PasteRepository;
import com.pastebin.pasterbin.service.BlobStorageService;
import com.pastebin.pasterbin.service.PasteService;
import com.pastebin.pasterbin.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PasteBinController {

    private final BlobStorageService blobStorageService;
    private final PasteService pasteService;

    @Autowired
    public PasteBinController(BlobStorageService blobStorageService, PasteService pasteService) {
        this.blobStorageService = blobStorageService;
        this.pasteService = pasteService;
    }

    @PostMapping("/add/like")
    public void addLike(@RequestParam String title) {
        pasteService.addLike(title);
    }

    @PostMapping("/add/view")
    public void addView(@RequestParam String title) {
        pasteService.addView(title);
    }

    @GetMapping("/get/{postName}")
    public Paste getPost(@PathVariable String postName) {
        return pasteService.getPaste(postName);
    }

    @GetMapping("/get/link/{type}/{fileName}")
    public String getLinkOnContentByType(@PathVariable ContentType type, @PathVariable String fileName) {
        return blobStorageService.generateSasToken(fileName, type);
    }

    @PostMapping("/save/post")
    public void savePost(@RequestBody PasteRequest pasteRequest) {
        pasteService.save(pasteRequest);
    }


}
