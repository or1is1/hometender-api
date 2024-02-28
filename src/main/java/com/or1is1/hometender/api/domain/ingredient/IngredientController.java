package com.or1is1.hometender.api.domain.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {
	private final IngredientService ingredientService;
}
