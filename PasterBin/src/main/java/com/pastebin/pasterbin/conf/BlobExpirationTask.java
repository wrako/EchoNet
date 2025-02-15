package com.pastebin.pasterbin.conf;

import com.pastebin.pasterbin.entity.Paste;
import com.pastebin.pasterbin.repo.PasteRepository;
import com.pastebin.pasterbin.service.BlobStorageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class BlobExpirationTask {

    private final PasteRepository pasteRepository;
    private final BlobStorageService blobStorageService;

    public BlobExpirationTask(PasteRepository pasteRepository, BlobStorageService blobStorageService) {
        this.pasteRepository = pasteRepository;
        this.blobStorageService = blobStorageService;
    }

    @Scheduled(fixedRate = 60000) // Проверка каждые 60 секунд
    public void deleteExpiredBlobs() {
        List<Paste> expiredPastes = pasteRepository.findAllByExpirationTimeBefore(Instant.now());
        for (Paste paste : expiredPastes) {
            blobStorageService.deleteBlob(paste.getTitle());
            pasteRepository.delete(paste);
        }
    }
}
