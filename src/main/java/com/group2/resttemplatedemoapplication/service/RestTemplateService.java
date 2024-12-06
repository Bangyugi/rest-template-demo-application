package com.group2.resttemplatedemoapplication.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RestTemplateService {
    <T>T convertToObject(String json, Class<T> t) throws JsonProcessingException;
}
