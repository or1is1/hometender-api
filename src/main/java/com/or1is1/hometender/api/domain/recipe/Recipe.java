package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Recipe {
	@Id
	@GeneratedValue
	private Long recipeId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member writer;

	private String name;

	private String description;

	@Enumerated(STRING)
	private CraftMethod craftMethod;

	@OneToMany(mappedBy = "recipe", cascade = ALL)
	private List<RecipeIngredient> recipeIngredientList;

	private String manual;

	public Recipe(Member writer, String name, String description, CraftMethod craftMethod,
	              List<RecipeIngredientDto> recipeIngredientList, String manual) {

		this.writer = writer;

		put(name, description, craftMethod, recipeIngredientList, manual);
	}

	public void put(String name, String description, CraftMethod craftMethod,
	                List<RecipeIngredientDto> recipeIngredientList, String manual) {

		this.name = name;
		this.description = description;
		this.craftMethod = craftMethod;
		this.recipeIngredientList = recipeIngredientList.stream()
				.map(recipeIngredientDto -> recipeIngredientDto.toEntity(this))
				.toList();
		this.manual = manual;
	}
}
