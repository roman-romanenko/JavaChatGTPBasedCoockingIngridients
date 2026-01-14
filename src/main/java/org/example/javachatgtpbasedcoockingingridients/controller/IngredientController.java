package org.example.javachatgtpbasedcoockingingridients.controller;

import lombok.RequiredArgsConstructor;
import org.example.javachatgtpbasedcoockingingridients.model.IngredientRequest;
import org.example.javachatgtpbasedcoockingingridients.service.IngredientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public String categorizeIngredient(@RequestBody IngredientRequest requestBody) {
        return ingredientService.categorizeIngredient(requestBody.ingredient());
    }

}