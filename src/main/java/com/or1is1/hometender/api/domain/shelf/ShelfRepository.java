package com.or1is1.hometender.api.domain.shelf;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
	List<Shelf> findByWriter(Member writer);

	void deleteByWriterAndIngredient(Member writer, Ingredient ingredient);
}
