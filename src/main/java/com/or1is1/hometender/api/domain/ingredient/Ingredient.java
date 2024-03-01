package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Ingredient {
	@Id
	@GeneratedValue
	private Long ingredientId;

	@ManyToOne(fetch = LAZY)
	private Member writer;

	@NotBlank
	private String name;

	private String description;

	private float volume; // 알콜 도수

	Ingredient(Member writer, String name, String description, float volume) {
		this.writer = writer;
		this.name = name;
		this.description = description;
		this.volume = volume;
	}

	public void putIngredient(String name, String description, float volume) {
		this.name = name;
		this.description = description;
		this.volume = volume;
	}
}
