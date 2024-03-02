package com.or1is1.hometender.api.domain.recipe.dto.request;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;

import java.util.List;

public record PostRecipeRequest(
		String name,
		String description,
		List<RecipeIngredientDto> recipeIngredientList,
		CraftMethod craftMethod,
		String manual
) {
}
