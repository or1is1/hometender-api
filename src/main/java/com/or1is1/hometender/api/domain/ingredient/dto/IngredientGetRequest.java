package com.or1is1.hometender.api.domain.ingredient.dto;

import jakarta.validation.constraints.NotBlank;

public record IngredientGetRequest(
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String name
) {
}
