package com.or1is1.hometender.api.domain.recipe.dto.response;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;
import com.or1is1.hometender.api.domain.recipe.Recipe;

public record GetRecipeResponse(
		String name,
		String description,
		CraftMethod craftMethod,
		String manual
) {
	public GetRecipeResponse(Recipe recipe) {
		this(
				recipe.getName(),
				recipe.getDescription(),
				recipe.getCraftMethod(),
				recipe.getManual()
		);
	}
}
