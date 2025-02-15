package com.pastebin.pasterbin.service;


import com.pastebin.pasterbin.dto.PasteRequest;
import com.pastebin.pasterbin.entity.Paste;
import com.pastebin.pasterbin.repo.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class PasteService {

    @Autowired
    private PasteRepository repository;

    public void save(PasteRequest pasteRequest) {
        Paste paste = new Paste();
        paste.setMediaList(pasteRequest.getMediaList());

        paste.setCreatedAt(pasteRequest.getCreatedAt()); paste.setUpdatedAt(pasteRequest.getCreatedAt());
        paste.setViews(0); paste.setLikes(0);

        paste.setTitle(pasteRequest.getTitle());
        paste.setEdited(false);
    }

    public void addLike(String title) {
        Paste paste = repository.findByTitle(title).getFirst();
        paste.setLikes(paste.getLikes() + 1);
        repository.save(paste);
    }

    public void addView(String title) {
        Paste paste = repository.findByTitle(title).getFirst();
        paste.setViews(paste.getViews() + 1);
        repository.save(paste);
    }
    public Paste getPaste(String title) {
        return repository.findByTitle(title).getFirst();
    }

}
