package com.or1is1.hometender.api.domain.ingredient.dto;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;

public record GetIngredientResponse(
		String memberNickname,
		String name,
		String description,
		float volume
) {
	public GetIngredientResponse(Ingredient ingredient) {
		this(
				ingredient.getWriter().getNickname(),
				ingredient.getName(),
				ingredient.getDescription(),
				ingredient.getVolume()
		);
	}
}
