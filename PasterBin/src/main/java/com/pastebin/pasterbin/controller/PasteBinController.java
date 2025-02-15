package com.pastebin.pasterbin.controller;

import com.pastebin.pasterbin.dto.PasteRequest;
import com.pastebin.pasterbin.entity.ContentType;
import com.pastebin.pasterbin.entity.Paste;
import com.pastebin.pasterbin.repo.PasteRepository;
import com.pastebin.pasterbin.service.BlobStorageService;
import com.pastebin.pasterbin.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


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

//    @PostMapping("/save")
//    public String saveText(@RequestBody PasteRequest request) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        String externalServiceUrl = "http://localhost:8081/gen";
//
//        ResponseEntity<String> response = restTemplate.getForEntity(externalServiceUrl, String.class);
//
//        String fileName;
//        if (response.getStatusCode().is2xxSuccessful()) {
//            fileName =  response.getBody(); // Возвращаем ответ от внешнего сервиса
//        } else {
//            throw new RuntimeException("Failed to send data to external service");
//        }
//        System.out.println("\n\n" +fileName+"\n\n");
//        return blobStorageService.saveTextAndGenerateLink(fileName, request.getText(), request.getLifeTime());
//    }


    @GetMapping("/delete/{fileName}")
    public void deleteBlob(@PathVariable String fileName) {
        blobStorageService.deleteBlob(fileName);
        redisService.delete(fileName);
    }

    @GetMapping("/{fileName}")
    public String getBlobContent(@PathVariable String fileName) {
        String answer = redisService.get(fileName);

        if (answer == null) { // if cant find text in cache take it in blob storage
            answer = blobStorageService.getBlobContentAsText(fileName);
            redisService.save(fileName, answer);
            return answer;
        }
        return answer;
    }
    @GetMapping("/get/{postName}")
    public Paste getPost(@PathVariable String postName) {
        return pasteRepository.findByTitle(postName).getFirst();
    }
//    @PostMapping("/save")
//    public String saveText(@RequestBody PasteRequest request) {
//
//        RestTemplate restTemplate = new RestTemplate();
//        String externalServiceUrl = "http://localhost:8081/gen";
//
//        ResponseEntity<String> response = restTemplate.getForEntity(externalServiceUrl, String.class);
//
//        String fileName;
//        if (response.getStatusCode().is2xxSuccessful()) {
//            fileName =  response.getBody(); // Возвращаем ответ от внешнего сервиса
//        } else {
//            throw new RuntimeException("Failed to send data to external service");
//        }
//        System.out.println("\n\n" +fileName+"\n\n");
//        return blobStorageService.saveTextAndGenerateLink(fileName, request.getText(), request.getLifeTime());
//    }
    @GetMapping("/get/link/{type}/{fileName}")
    public String getLinkOnContentByType(@PathVariable ContentType type, @PathVariable String fileName) {
        return blobStorageService.generateSasToken(fileName, type);
    }

}
