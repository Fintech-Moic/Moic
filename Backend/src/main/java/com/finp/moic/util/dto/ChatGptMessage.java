package com.finp.moic.util.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatGptMessage {

    String role;
    String content;
}
