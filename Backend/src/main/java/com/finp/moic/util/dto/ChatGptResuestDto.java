package com.finp.moic.util.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatGptResuestDto {

    private String model;

    private boolean stream;

    ChatGptMessage message;

    public ChatGptResuestDto(String model, boolean stream, ChatGptMessage message) {
        this.model = model;
        this.stream = stream;
        this.message = message;
    }
}
