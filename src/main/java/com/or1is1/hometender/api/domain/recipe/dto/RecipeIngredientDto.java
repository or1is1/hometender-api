package com.or1is1.hometender.api.domain.recipe.dto;

import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;
import com.or1is1.hometender.api.domain.recipe.SizeType;

public record RecipeIngredientDto(
		Long ingredientId,
		String ingredientName,
		float volume,
		float size,
		SizeType sizeType,
		boolean isOptional
) {
	public RecipeIngredientDto(RecipeIngredient recipeIngredient) {
		this(
				recipeIngredient.getIngredient().getIngredientId(),
				recipeIngredient.getIngredient().getName(),
				recipeIngredient.getIngredient().getVolume(),
				recipeIngredient.getSize(),
				recipeIngredient.getSizeType(),
				recipeIngredient.isOption()
		);
	}
}
