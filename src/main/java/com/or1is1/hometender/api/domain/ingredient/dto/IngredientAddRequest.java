package com.or1is1.hometender.api.domain.ingredient.dto;

import jakarta.validation.constraints.NotBlank;

public record IngredientAddRequest(
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String name,
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String description,
		float volume
) {
}
