package com.or1is1.hometender.api.domain.ingredient.dto.request;

public record IngredientPutRequest(
		String description,
		float volume
) {
}
