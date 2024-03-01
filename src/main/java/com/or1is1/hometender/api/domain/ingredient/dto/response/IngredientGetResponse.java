package com.or1is1.hometender.api.domain.ingredient.dto.response;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;

public record IngredientGetResponse(
		String memberNickname,
		String name,
		String description,
		float volume
) {
	public IngredientGetResponse(Ingredient ingredient) {
		this(
				ingredient.getWriter().getNickname(),
				ingredient.getName(),
				ingredient.getDescription(),
				ingredient.getVolume()
		);
	}
}
