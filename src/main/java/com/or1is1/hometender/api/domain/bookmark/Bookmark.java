package com.or1is1.hometender.api.domain.bookmark;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Bookmark {
	@Id
	@GeneratedValue
	private Long bookmarkId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "writer_id")
	private Member writer;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	public Bookmark(Member member, Recipe recipe) {
		put(member, recipe);
	}

	public void put(Member writer, Recipe recipe) {
		this.writer = writer;
		this.recipe = recipe;
	}
}
