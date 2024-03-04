package com.or1is1.hometender.api.domain.shelf;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
import com.or1is1.hometender.api.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShelfService {

	private final ShelfRepository shelfRepository;

	@Transactional
	public void post(Long ingredientId, Long loginId) {

		shelfRepository.save(new Shelf(new Member(loginId), new Ingredient(ingredientId)));
	}

	public List<IngredientDto> getList(Long loginId) {

		return shelfRepository.findByWriter(new Member(loginId))
				.stream().map(IngredientDto::new)
				.toList();
	}

	@Transactional
	public void delete(Long ingredientId, Long loginId) {

		shelfRepository.deleteByWriterAndIngredient(new Member(loginId), new Ingredient(ingredientId));
	}
}
