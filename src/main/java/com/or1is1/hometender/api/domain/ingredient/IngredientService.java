package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.ingredient.dto.IngredientAddRequest;
import com.or1is1.hometender.api.domain.ingredient.repository.IngredientRepository;
import com.or1is1.hometender.api.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
