package com.finp.moic.util.database.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatGptService {

    @Value("${CHAT_APIURL}")
    private String apiUrl;

    @Value("${CHAT_APIKEY")
    private String apiKey;

    public Map<String, String> response(List<String> texts) {

        String apiUrl = this.apiUrl;
        String apiKey = this.apiKey;


        Map<String,String> answer = new HashMap<String,String>();

        // 1. 질문에 따라 챗 GPT 호출

        // 2. WebClient 객체로 받음

        // 3. JsonNode로 content만 빼서

        // 4. Map으로 변환 후 준다.

        return answer;
    }

}
