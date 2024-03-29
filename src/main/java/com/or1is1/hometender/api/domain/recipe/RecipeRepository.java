package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	public List<Recipe> findByWriter(Member writer);

	public Optional<Recipe> findByRecipeIdAndWriter(Long recipeId, Member writer);

	public void deleteByRecipeIdAndWriter(Long recipeId, Member writer);
}
