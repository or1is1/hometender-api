package com.or1is1.hometender.api.domain.ingredient.dto;

public record IngredientPutRequest(
		String name,
		String description,
		float volume
) {
}
