package com.or1is1.hometender.api.domain.recipe.dto;

import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;
import com.or1is1.hometender.api.domain.recipe.SizeType;

public record RecipeIngredientDto(
		Long ingredientId,
		float size,
		SizeType sizeType,
		boolean isOptional
) {
	public RecipeIngredientDto(RecipeIngredient recipeIngredient) {
		this(
				recipeIngredient.getRecipeIngredientId(),
				recipeIngredient.getSize(),
				recipeIngredient.getSizeType(),
				recipeIngredient.isOption()
		);
	}
}
