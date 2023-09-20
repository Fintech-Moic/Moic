package com.finp.moic.autoComplete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AutoController {

    AutoCompleteService autoCompleteService;
    @Autowired
    public AutoController(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }
    @PostMapping("/autocomplete")
    public List<String> getAutoComplete(String word) {

        List<String> list = autoCompleteService.getAutoComplete(word);
        return list;
    }
}
