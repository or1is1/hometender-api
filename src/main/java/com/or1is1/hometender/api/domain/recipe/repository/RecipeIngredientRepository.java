package com.or1is1.hometender.api.domain.recipe.repository;

import com.or1is1.hometender.api.domain.recipe.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
}
