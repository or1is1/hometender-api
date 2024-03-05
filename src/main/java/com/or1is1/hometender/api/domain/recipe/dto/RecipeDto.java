package com.or1is1.hometender.api.domain.recipe.dto;

import com.or1is1.hometender.api.domain.bookmark.Bookmark;
import com.or1is1.hometender.api.domain.recipe.CraftMethod;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public record RecipeDto(
		String name,
		String description,
		CraftMethod craftMethod,
		List<RecipeIngredientDto> recipeIngredientList,
		String manual
) {
	public RecipeDto(Recipe recipe, List<RecipeIngredient> recipeIngredientList) {
		this(
				recipe.getName(),
				recipe.getDescription(),
				recipe.getCraftMethod(),
				recipeIngredientList.stream().map(RecipeIngredientDto::new).toList(),
				recipe.getManual()
		);
	}

	public RecipeDto(Bookmark bookmark) {
		this(bookmark.getRecipe(), new ArrayList<>());
	}
}
