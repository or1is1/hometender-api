package com.or1is1.hometender.api.domain.shelf;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Shelf {
	@Id
	@GeneratedValue
	private Long shelfId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id")
	private Member writer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	public Shelf(Member member, Ingredient ingredient) {
		put(member, ingredient);
	}

	public void put(Member writer, Ingredient ingredient) {
		this.writer = writer;
		this.ingredient = ingredient;
	}
}
