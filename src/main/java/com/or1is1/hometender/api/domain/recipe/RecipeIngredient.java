package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RecipeIngredient {
	@Id
	@GeneratedValue
	private Long RecipeIngredientId;

	@ManyToOne(fetch = LAZY)
	private Recipe recipe;

	@ManyToOne(fetch = LAZY)
	private Ingredient ingredient;

	private float size;

	@Enumerated(STRING)
	private SizeType sizeType;

	private boolean option;

	public RecipeIngredient(Recipe recipe, Ingredient ingredient, float size, SizeType sizeType, boolean option) {
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.size = size;
		this.sizeType = sizeType;
		this.option = option;
	}
}
