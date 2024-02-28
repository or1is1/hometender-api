package com.or1is1.hometender.api.domain.recipe.repository;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Ingredient, Long>, RecipeRepositoryInterface {
	public List<Ingredient> findByWriter(Member writer);
	public Optional<Ingredient> findByNameAndWriter(String name, Member writer);

	public void deleteByName(String name, Member writer);
}
