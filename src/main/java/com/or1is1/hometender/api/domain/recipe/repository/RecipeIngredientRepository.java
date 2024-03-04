package com.or1is1.hometender.api.domain.recipe.repository;

import com.or1is1.hometender.api.domain.recipe.Recipe;
import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
	List<RecipeIngredient> findByRecipe(Recipe recipe);

	public void deleteByRecipe(Recipe recipe);
}
