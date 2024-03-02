package com.or1is1.hometender.api.domain.recipe.dto.request;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;

import java.util.List;

public record PostRecipeRequest(
		String name,
		String description,
		List<PostRecipeIngredientRequest> recipeIngredientList,
		CraftMethod craftMethod,
		String manual
) {
}
