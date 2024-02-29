package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.ingredient.dto.IngredientAddRequest;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.ingredient.repository.IngredientRepository;
import com.or1is1.hometender.api.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
	private final IngredientRepository ingredientRepository;

	public void add(Long loginId, IngredientAddRequest addRequest) {
		Ingredient ingredient = new Ingredient(
				new Member(loginId),
				addRequest.name(),
				addRequest.description(),
				addRequest.volume()
		);

		ingredientRepository.save(ingredient);
	}

	public Ingredient get(String name, Long loginId) {
		return ingredientRepository.findByNameAndWriter(name, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);
	}

	public List<Ingredient> getList(Long loginId) {
		return ingredientRepository.findByWriter(new Member(loginId));
	}
}
