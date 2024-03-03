package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
	private Member writer;

	private String name;

	private String description;

	@Enumerated(STRING)
	private CraftMethod craftMethod;

	private String manual;

	public Recipe(Member writer, String name, String description, CraftMethod craftMethod, String manual) {
		this.writer = writer;
		this.name = name;
		this.description = description;
		this.craftMethod = craftMethod;
		this.manual = manual;
	}

	public void put(String name, String description, CraftMethod craftMethod, String manual) {
		this.name = name;
		this.description = description;
		this.craftMethod = craftMethod;
		this.manual = manual;
	}
}
