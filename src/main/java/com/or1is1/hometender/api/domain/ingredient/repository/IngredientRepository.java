package com.or1is1.hometender.api.domain.ingredient.repository;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, IngredientRepositoryInterface {
}
