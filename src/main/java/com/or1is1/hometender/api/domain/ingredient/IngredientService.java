package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.ingredient.dto.PostAndPutIngredientRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.GetIngredientResponse;
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
	public void post(Long loginId, PostAndPutIngredientRequest addRequest) {

		Ingredient ingredient = new Ingredient(
				new Member(loginId),
				addRequest.name(),
				addRequest.description(),
				addRequest.volume()
		);

		ingredientRepository.save(ingredient);
	}

	public List<GetIngredientResponse> getList(Long loginId) {

		return ingredientRepository.findByWriter(new Member(loginId))
				.stream().map(GetIngredientResponse::new)
				.toList();
	}

	public GetIngredientResponse get(Long ingredientId, Long loginId) {

		Ingredient ingredient = ingredientRepository.findByIngredientIdAndWriter(ingredientId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		return new GetIngredientResponse(ingredient);
	}

	@Transactional
	public void put(Long ingredientId, Long loginId, PostAndPutIngredientRequest postAndPutIngredientRequest) {

		Ingredient ingredient = ingredientRepository.findByIngredientIdAndWriter(ingredientId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		if (!loginId.equals(ingredient.getWriter().getId())) {
			throw new IngredientIsNotMineException();
		}

		ingredient.putIngredient(
				postAndPutIngredientRequest.name(),
				postAndPutIngredientRequest.description(),
				postAndPutIngredientRequest.volume()
		);
	}

	@Transactional
	public void delete(Long ingredientId, Long loginId) {

		ingredientRepository.deleteByIngredientIdAndWriter(ingredientId, new Member(loginId));
	}
}
