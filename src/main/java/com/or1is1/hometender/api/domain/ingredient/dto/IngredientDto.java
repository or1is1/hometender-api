package com.or1is1.hometender.api.domain.ingredient.dto;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.shelf.Shelf;
import jakarta.validation.constraints.NotBlank;

public record IngredientDto(
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String name,
		@NotBlank(message = "{validation.constraints.NotBlank}")
		String description,
		float volume
) {
	public IngredientDto(Ingredient ingredient) {
		this(
				ingredient.getName(),
				ingredient.getDescription(),
				ingredient.getVolume()
		);
	}

	public IngredientDto(Shelf shelf) {
		this(
				shelf.getIngredient().getName(),
				shelf.getIngredient().getDescription(),
				shelf.getIngredient().getVolume()
		);
	}
}
