package com.group2.resttemplatedemoapplication.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Recipe {
    Integer id;
    String source_url;
    String image_url;
    String title;
    int serving;
    int cookingTime;
    String publisher;
    List<Ingredient> ingredients = new ArrayList<>();
}
