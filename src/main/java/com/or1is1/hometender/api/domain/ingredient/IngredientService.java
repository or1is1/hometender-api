package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientIsNotMineException;
import com.or1is1.hometender.api.domain.ingredient.repository.IngredientRepository;
import com.or1is1.hometender.api.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

	private final IngredientRepository ingredientRepository;

	@Transactional
	public void post(Long loginId, IngredientDto addRequest) {

		Ingredient ingredient = new Ingredient(
				new Member(loginId),
				addRequest.name(),
				addRequest.description(),
				addRequest.volume()
		);

		ingredientRepository.save(ingredient);
	}

	public List<IngredientDto> getList(Long loginId) {

		return ingredientRepository.findByWriter(new Member(loginId))
				.stream().map(IngredientDto::new)
				.toList();
	}

	public IngredientDto get(Long ingredientId, Long loginId) {

		Ingredient ingredient = ingredientRepository.findByIngredientIdAndWriter(ingredientId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		return new IngredientDto(ingredient);
	}

	@Transactional
	public void put(Long ingredientId, Long loginId, IngredientDto ingredientDto) {

		Ingredient ingredient = ingredientRepository.findByIngredientIdAndWriter(ingredientId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		if (!loginId.equals(ingredient.getWriter().getId())) {
			throw new IngredientIsNotMineException();
		}

		ingredient.putIngredient(
				ingredientDto.name(),
				ingredientDto.description(),
				ingredientDto.volume()
		);
	}

	@Transactional
	public void delete(Long ingredientId, Long loginId) {

		ingredientRepository.deleteByIngredientIdAndWriter(ingredientId, new Member(loginId));
	}
}
