package com.pastebin.pasterbin.controller;

import com.pastebin.pasterbin.dto.PasteRequest;
import com.pastebin.pasterbin.entity.ContentType;
import com.pastebin.pasterbin.entity.Paste;
import com.pastebin.pasterbin.repo.PasteRepository;
import com.pastebin.pasterbin.service.BlobStorageService;
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
    private final RedisService redisService;
    private final PasteRepository pasteRepository;

    @Autowired
    public PasteBinController(BlobStorageService blobStorageService, RedisService redisService, PasteRepository pasteRepository) {
        this.blobStorageService = blobStorageService;
        this.redisService = redisService;
        this.pasteRepository = pasteRepository;
    }

    @PostMapping("/add/like")
    public void addLike(@RequestParam String fileName) {
        Paste paste = pasteRepository.findByTitle(fileName).getFirst();
        paste.setLikes(paste.getLikes() + 1);
        pasteRepository.save(paste);
    }

    @PostMapping("/add/view")
    public void addView(@RequestParam String fileName) {
        Paste paste = pasteRepository.findByTitle(fileName).getFirst();
        paste.setViews(paste.getViews() + 1);
        pasteRepository.save(paste);
    }

    @GetMapping("/get/{postName}")
    public Paste getPost(@PathVariable String postName) {
        return pasteRepository.findByTitle(postName).getFirst();
    }

    @GetMapping("/get/link/{type}/{fileName}")
    public String getLinkOnContentByType(@PathVariable ContentType type, @PathVariable String fileName) {
        return blobStorageService.generateSasToken(fileName, type);
    }

    @PostMapping("/save/post")
    public String savePost(@RequestBody PasteRequest pasteRequest) {
        Paste paste = new Paste();
        paste.setMediaList(pasteRequest.getMediaList());
        paste.setLikes(0); paste.setViews(0);
        System.out.println("\n\n" +fileName+"\n\n");
        return blobStorageService.saveTextAndGenerateLink(fileName, request.getText(), request.getLifeTime());
    }


}
