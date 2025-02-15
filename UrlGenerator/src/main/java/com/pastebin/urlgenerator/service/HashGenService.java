package com.pastebin.urlgenerator.service;

import com.pastebin.urlgenerator.entity.Url;
import com.pastebin.urlgenerator.repo.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class HashGenService {

    @Autowired
    private UrlRepository urlRepository;

    public String generateHash() {
        try {
            String input = UUID.randomUUID().toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));

            return sb.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    public String generateUniqueUrl() {
        String uniqueUrl;
        do { uniqueUrl = generateHash();
        } while (!urlRepository.findByUrl(uniqueUrl).isEmpty());

        urlRepository.save(new Url(uniqueUrl));
        return uniqueUrl;
    }
}