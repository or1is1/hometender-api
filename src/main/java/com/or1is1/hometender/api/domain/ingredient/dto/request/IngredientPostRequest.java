package com.or1is1.hometender.api.domain.ingredient.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IngredientPostRequest(
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String name,
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String description,
		float volume
) {
}
