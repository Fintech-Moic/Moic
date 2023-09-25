package com.finp.moic.autoComplete.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AutoCompleteService {

    RedisTemplate<String, String> redisTemplate;
    private final String key;
    @Autowired
    public AutoCompleteService(@Qualifier("AutoRedis") RedisTemplate<String, String> redisTemplate,
                               @Value("${autocomplete-key}")String key) {
        this.redisTemplate = redisTemplate;
        this.key = key;
    }

    public List<String> getAutoComplete(String word) {

        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        List<String> autoCompleteList = new ArrayList<String>();

        Long rank = zSetOperations.rank(key,word);

        if (rank != null) {
            Set<String> rangeList = zSetOperations.range(key,rank,rank + 1000);
            autoCompleteList = rangeList.stream().filter(value -> value.endsWith("*") && value.startsWith(word))
                    .map(value -> value.substring(0,value.length() - 1))
                    .limit(8)
                    .toList();
        }

        return autoCompleteList;
    }
}
