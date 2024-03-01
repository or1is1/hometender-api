package com.or1is1.hometender.api.domain.recipe.dto.request;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;

import java.util.List;

public record RecipePostRequest(
		String name,
		String description,
		List<RecipeIngredientRequest> recipeIngredientList,
		CraftMethod craftMethod,
		String manual
) {
}
