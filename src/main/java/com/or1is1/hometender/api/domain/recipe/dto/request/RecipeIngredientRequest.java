package com.or1is1.hometender.api.domain.recipe.dto.request;

import com.or1is1.hometender.api.domain.recipe.SizeType;

public record RecipeIngredientRequest(
		Long ingredientId,
		float size,
		SizeType sizeType,
		boolean isOptional
) {
}
