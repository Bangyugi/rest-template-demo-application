package com.group2.resttemplatedemoapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.resttemplatedemoapplication.entity.Recipe;
import com.group2.resttemplatedemoapplication.service.RestTemplateService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class RecipeController {
    private final RestTemplateService restTemplateService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping("/{id}")
    public Recipe getForObject(@PathVariable("id") int id) {
        return restTemplate.getForObject("/" + id, Recipe.class);
    }

    @PostMapping("")
    public Recipe postForObject(@RequestBody Recipe recipe) throws IOException {
//        byte[] imageContent = image.getBytes();
//        String imageBase64 = Base64.getEncoder().encodeToString(imageContent);
//        recipe.setImage_url(imageBase64);
        return restTemplate.postForObject("/",recipe,Recipe.class);
    }

    @PutMapping("/{id}")
    public Recipe putForObject(@PathVariable("id") int id,@RequestBody Recipe recipe){
        return restTemplate.exchange("/"+id,
                HttpMethod.PUT,
                new HttpEntity<>(recipe),
                Recipe.class,
                Integer.toString(id)).getBody();
    }

    @DeleteMapping("/{id}")
    public String deleteEx(@PathVariable("id") int id){
        return restTemplate.exchange("/"+id,
                HttpMethod.DELETE,
                new HttpEntity<>("Delete successfully"),
                String.class,
                Integer.toString(id)).getBody();
    }
}
