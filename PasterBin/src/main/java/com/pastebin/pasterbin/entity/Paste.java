package com.pastebin.pasterbin.entity;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "posts")  // MongoDB collection name
public class Paste {
    
    @Id
    private String id;
    private String title;

///  For future update
    private int views;
    private int likes;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean isEdited;

    private List<Map<ContentType, String>> mediaList;

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.toInstant(ZoneOffset.UTC);
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt.toInstant(ZoneOffset.UTC);
    }
}


