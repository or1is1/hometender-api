package com.or1is1.hometender.api.dto;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;
import com.or1is1.hometender.api.domain.recipe.Recipe;

public record GetRecipeListResponse(
		String name,
		String description,
		CraftMethod craftMethod
) {
	public GetRecipeListResponse(Recipe recipe) {
		this(
				recipe.getName(),
				recipe.getDescription(),
				recipe.getCraftMethod()
		);
	}
}
