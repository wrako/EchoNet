package com.pastebin.urlgenerator.repo;

import com.pastebin.urlgenerator.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {
    List<Url> findByUrl(String url);
}
