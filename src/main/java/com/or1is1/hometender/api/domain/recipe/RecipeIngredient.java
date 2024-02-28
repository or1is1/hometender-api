package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
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
}
