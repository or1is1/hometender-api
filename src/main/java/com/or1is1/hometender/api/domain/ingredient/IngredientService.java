package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.domain.ingredient.dto.request.IngredientPutRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.request.IngredientPostRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.response.IngredientGetResponse;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
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
	public void post(Long loginId, IngredientPostRequest addRequest) {
		Ingredient ingredient = new Ingredient(
				new Member(loginId),
				addRequest.name(),
				addRequest.description(),
				addRequest.volume()
		);

		ingredientRepository.save(ingredient);
	}

	public IngredientGetResponse get(String name, Long loginId) {
		Ingredient ingredient = ingredientRepository.findByNameAndWriter(name, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		return new IngredientGetResponse(ingredient);
	}

	public List<IngredientGetResponse> getList(Long loginId) {

		return ingredientRepository.findByWriter(new Member(loginId))
				.stream().map(IngredientGetResponse::new)
				.toList();
	}

	@Transactional
	public void put(String name, Long loginId, IngredientPutRequest ingredientPutRequest) {
		Ingredient ingredient = ingredientRepository.findByNameAndWriter(name, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		ingredient.putIngredient(
				name,
				ingredientPutRequest.description(),
				ingredientPutRequest.volume()
		);
	}

	public void delete(String name, Long loginId) {
		ingredientRepository.deleteByName(name, new Member(loginId));
	}
}
