package com.or1is1.hometender.api.domain.recipe.dto;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;

import java.util.List;

public record PostAndPutRecipeRequest(
		String name,
		String description,
		List<RecipeIngredientDto> recipeIngredientList,
		CraftMethod craftMethod,
		String manual
) {
}
