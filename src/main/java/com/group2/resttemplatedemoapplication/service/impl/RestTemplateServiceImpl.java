package com.group2.resttemplatedemoapplication.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.resttemplatedemoapplication.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestTemplateServiceImpl implements RestTemplateService {
    private final ObjectMapper objectMapper;

    @Override
    public <T> T convertToObject(String json, Class<T> t) throws JsonProcessingException {
        return objectMapper.readValue(json, t);
    }
}
