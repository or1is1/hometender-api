package com.or1is1.hometender.api.domain.recipe.dto.request;

import com.or1is1.hometender.api.domain.recipe.CraftMethod;

public record RecipePutRequest(
		String name,
		String description,
		CraftMethod craftMethod,
		String manual
) {
}
