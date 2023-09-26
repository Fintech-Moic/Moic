package com.finp.moic.util.database.service;

import com.finp.moic.util.dto.ChatGptMessage;
import com.finp.moic.util.dto.ChatGptResuestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    @Value("${CHAT_APIURL}")
    private String apiUrl;

    @Value("${CHAT_APIKEY")
    private String apiKey;

    @Value("${CHAT_MODEL}")
    private String model;


    public Map<String, String> response(List<String> texts) {

        String apiUrl = this.apiUrl;
        String apiKey = "Bearer " + this.apiKey;

        // WebClient 객체 생성 -> 비동기 선택. 챗 GPT 서비스는 응답에 따라 매우 느리므로 Blocking 방식은 지양한다.
        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION,apiKey)
                .build();

        ChatGptResuestDto chatGptResuestDto = ChatGptResuestDto.builder()
                .model(model)
                .stream(false)
                .message(ChatGptMessage.builder()
                        .role("user")
                        .content("안녕?").build())
                .build();

        Mono<String> responseMono = webClient.post()
                .uri("")
                .bodyValue(chatGptResuestDto)
                .retrieve()
                .bodyToMono(String.class);

        System.out.println(responseMono);

        Map<String,String> answer = new HashMap<String,String>();

        // 1. 질문에 따라 챗 GPT 호출

        // 2. WebClient 객체로 받음

        // 3. JsonNode로 content만 빼서

        // 4. Map으로 변환 후 준다.

        return answer;
    }

}
