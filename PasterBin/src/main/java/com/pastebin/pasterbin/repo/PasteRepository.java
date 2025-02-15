package com.pastebin.pasterbin.repo;
import com.pastebin.pasterbin.entity.Paste;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface PasteRepository extends MongoRepository<Paste, String> {
    List<Paste> findByTitle(String title);
    List<Paste> findAllByExpirationTimeBefore(Instant time);

}
