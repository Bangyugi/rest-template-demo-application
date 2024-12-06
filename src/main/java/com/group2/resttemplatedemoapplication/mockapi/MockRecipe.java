package com.group2.resttemplatedemoapplication.mockapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group2.resttemplatedemoapplication.entity.Ingredient;
import com.group2.resttemplatedemoapplication.entity.Recipe;
import lombok.*;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.util.*;


@RestController
@RequestMapping("/mock/recipe")
@RequiredArgsConstructor
public class MockRecipe {
    private static Map<Integer, Recipe> recipes;

    static {
        recipes = new HashMap<>();
        recipes.put(1, new Recipe(
                1,
                "https://example.com/spaghetti",
                "https://example.com/spaghetti.jpg",
                "Spaghetti Bolognese",
                4,
                30,
                "Chef John",
                Arrays.asList(
                        new Ingredient("Spaghetti", 200, "grams"),
                        new Ingredient("Ground Beef", 300, "grams"),
                        new Ingredient("Tomato Sauce", 2, "cups"),
                        new Ingredient("Onion", 1, "medium"),
                        new Ingredient("Garlic", 2, "cloves")
                )
        ));

        recipes.put(2, new Recipe(
                2,
                "https://example.com/pancakes",
                "https://example.com/pancakes.jpg",
                "Fluffy Pancakes",
                2,
                20,
                "Baking Queen",
                Arrays.asList(
                        new Ingredient("Flour", 200, "grams"),
                        new Ingredient("Milk", 250, "ml"),
                        new Ingredient("Eggs", 2, "pieces"),
                        new Ingredient("Sugar", 2, "tablespoons"),
                        new Ingredient("Butter", 50, "grams")
                )
        ));

        recipes.put(3, new Recipe(
                3,
                "https://example.com/caesarsalad",
                "https://example.com/caesarsalad.jpg",
                "Caesar Salad",
                3,
                15,
                "Healthy Chef",
                Arrays.asList(
                        new Ingredient("Lettuce", 1, "head"),
                        new Ingredient("Croutons", 100, "grams"),
                        new Ingredient("Parmesan Cheese", 50, "grams"),
                        new Ingredient("Caesar Dressing", 3, "tablespoons"),
                        new Ingredient("Chicken Breast", 1, "piece")
                )
        ));

        recipes.put(4, new Recipe(
                4,
                "https://example.com/sushiplatter",
                "https://example.com/sushiplatter.jpg",
                "Sushi Platter",
                4,
                60,
                "Sushi Master",
                Arrays.asList(
                        new Ingredient("Sushi Rice", 2, "cups"),
                        new Ingredient("Nori Sheets", 5, "pieces"),
                        new Ingredient("Salmon", 200, "grams"),
                        new Ingredient("Avocado", 1, "piece"),
                        new Ingredient("Soy Sauce", 50, "ml")
                )
        ));

        recipes.put(5, new Recipe(
                5,
                "https://example.com/chocolatecake",
                "https://example.com/chocolatecake.jpg",
                "Chocolate Cake",
                8,
                45,
                "Pastry Chef",
                Arrays.asList(
                        new Ingredient("Flour", 250, "grams"),
                        new Ingredient("Cocoa Powder", 50, "grams"),
                        new Ingredient("Sugar", 200, "grams"),
                        new Ingredient("Eggs", 3, "pieces"),
                        new Ingredient("Butter", 100, "grams")
                )
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getEmployee(@PathVariable int id) {
        try {
            if (!recipes.containsKey(id)) {
                throw new RuntimeException("Recipe not found");
            }
            Recipe recipe = recipes.get(id);
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Recipe not found");
        }
    }

    @PostMapping(path = "" ,consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createRecipe(@RequestPart("image") MultipartFile image, @RequestBody Recipe recipe) {
        try {
            if (recipes.containsKey(recipe.getId())) {
                throw new RuntimeException("Recipe already exists");
            }
            byte[] imageContent = image.getBytes();
            String imageBase64 = Base64.getEncoder().encodeToString(imageContent);
            System.out.println(imageBase64);
            recipe.setImage_url(imageBase64);
            recipes.put(recipe.getId(), recipe);
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") Integer id, @RequestBody Recipe recipe) {
        try {
            if (!recipes.containsKey(id)) {
                throw new RuntimeException("Recipe not found");
            }
            Recipe updatedRecipe = recipes.get(id);
            updatedRecipe.setIngredients(recipe.getIngredients());
            updatedRecipe.setCookingTime(recipe.getCookingTime());
//            byte[] imageContent = image.getBytes();
//            String imageBase64 = Base64.getEncoder().encodeToString(imageContent);
//            System.out.println(imageBase64);
//            updatedRecipe.setImage_url(imageBase64);
            updatedRecipe.setPublisher(recipe.getPublisher());
            updatedRecipe.setServing(recipe.getServing());
            updatedRecipe.setSource_url(recipe.getSource_url());

            return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable("id") Integer id) {
        try {
            if (!recipes.containsKey(id)) {
                throw new RuntimeException("Recipe not found");
            }
            recipes.remove(id);
            return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

