package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.request.RecipePostRequest;
import com.or1is1.hometender.api.domain.recipe.dto.request.RecipePutRequest;
import com.or1is1.hometender.api.domain.recipe.dto.response.RecipeGetResponse;
import com.or1is1.hometender.api.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
	private final RecipeRepository recipeRepository;

	@Transactional
	public void post(Long loginId, RecipePostRequest postRequest) {
		Recipe recipe = new Recipe(
				new Member(loginId),
				postRequest.name(),
				postRequest.description(),
				postRequest.craftMethod(),
				postRequest.manual()
		);

		recipeRepository.save(recipe);
	}

	public RecipeGetResponse get(String name, Long loginId) {
		Recipe recipe = recipeRepository.findByWriterAndName(new Member(loginId), name)
				.orElseThrow(IngredientCanNotFindException::new);

		return new RecipeGetResponse(recipe);
	}

	public List<RecipeGetResponse> getList(Long loginId) {

		return recipeRepository.findByWriter(new Member(loginId))
				.stream().map(RecipeGetResponse::new)
				.toList();
	}

	@Transactional
	public void put(String name, Long loginId, RecipePutRequest recipePutRequest) {
		Recipe recipe = recipeRepository.findByWriterAndName(new Member(loginId), name)
				.orElseThrow(IngredientCanNotFindException::new);

		recipe.put(
				name,
				recipePutRequest.description(),
				recipePutRequest.craftMethod(),
				recipePutRequest.manual()
		);
	}

	public void delete(String name, Long loginId) {
		recipeRepository.deleteByWriterAndName(new Member(loginId), name);
	}
}
