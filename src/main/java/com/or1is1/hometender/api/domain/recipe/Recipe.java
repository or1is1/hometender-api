package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
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

	private boolean isOfficial;
}
