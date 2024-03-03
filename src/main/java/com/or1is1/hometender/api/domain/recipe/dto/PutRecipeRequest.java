package com.or1is1.hometender.api.domain.recipe.dto;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;

public record PutRecipeRequest(
		String name,
		String description,
		CraftMethod craftMethod,
		String manual
) {
}
