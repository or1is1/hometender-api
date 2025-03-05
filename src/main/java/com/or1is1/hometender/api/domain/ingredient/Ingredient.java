package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.member.Member;
import jakarta.persistence.*;
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
	@JoinColumn(name = "member_id")
	private Member writer;

	@NotBlank
	private String name;

	private String description;

	private float volume; // 알콜 도수

	public Ingredient(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Ingredient(Member writer, String name, String description, float volume) {
		this.writer = writer;
		put(name, description, volume);
	}

	public void put(String name, String description, float volume) {
		this.name = name;
		this.description = description;
		this.volume = volume;
	}
}
