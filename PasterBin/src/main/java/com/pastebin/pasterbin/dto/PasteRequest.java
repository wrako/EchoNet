package com.pastebin.pasterbin.dto;

import com.pastebin.pasterbin.entity.ContentType;
import lombok.Data;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class PasteRequest {

    private String title;

    private Instant createdAt;

    private List<Map<ContentType, String>> mediaList;

}