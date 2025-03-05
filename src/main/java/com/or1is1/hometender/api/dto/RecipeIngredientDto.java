package com.or1is1.hometender.api.dto;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;
import com.or1is1.hometender.api.domain.recipe.SizeType;

public record RecipeIngredientDto(
		Long ingredientId,
		String ingredientName,
		float volume,
		float size,
		SizeType sizeType,
		boolean isOption
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

	public RecipeIngredient toEntity(Recipe recipe) {
		return new RecipeIngredient(recipe, new Ingredient(ingredientId), size, sizeType, isOption);
	}
}
