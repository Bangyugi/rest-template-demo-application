package com.group2.resttemplatedemoapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.resttemplatedemoapplication.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecipeController {

    private final RestTemplate restTemplate;



    @GetMapping("/{id}")
    public Recipe getForObject(@PathVariable("id") int id) {
        return restTemplate.getForObject("/" + id, Recipe.class);
    }

    @PostMapping("")
    public ResponseEntity<?> postForObject(@RequestPart("image") MultipartFile image, @RequestPart("recipe") Recipe recipe) throws IOException {

        if (!image.isEmpty()){
            try{

            byte[] bytes = image.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(Objects.requireNonNull(image.getOriginalFilename())));
            stream.write(bytes);
            stream.close();
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());

            }
        }

        MultiValueMap<String,Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("recipe", recipe);
        parameters.add("image", new FileSystemResource(Objects.requireNonNull(image.getOriginalFilename())));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        return ResponseEntity.ok( restTemplate.exchange("",  HttpMethod.POST,  new HttpEntity<MultiValueMap<String, Object>>(parameters, headers), Map.class).getBody());


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
