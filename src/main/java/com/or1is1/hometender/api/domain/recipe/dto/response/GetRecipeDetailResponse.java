package com.or1is1.hometender.api.domain.recipe.dto.response;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;

import java.util.List;

public record GetRecipeDetailResponse(
		String writer,
		String name,
		String description,
		CraftMethod craftMethod,
		List<RecipeIngredientDto> recipeIngredientList,
		String manual,
		boolean isOfficial
) {
	public GetRecipeDetailResponse(Recipe recipe, List<RecipeIngredient> recipeIngredientList) {
		this(
				recipe.getWriter().getNickname(),
				recipe.getName(),
				recipe.getDescription(),
				recipe.getCraftMethod(),
				recipeIngredientList.stream().map(RecipeIngredientDto::new).toList(),
				recipe.getManual(),
				recipe.isOfficial()
		);
	}
}
