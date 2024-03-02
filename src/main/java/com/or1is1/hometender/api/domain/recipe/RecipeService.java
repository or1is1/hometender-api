package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;
import com.or1is1.hometender.api.domain.recipe.dto.request.PostRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.request.PutRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.response.GetRecipeDetailResponse;
import com.or1is1.hometender.api.domain.recipe.dto.response.GetRecipeListResponse;
import com.or1is1.hometender.api.domain.recipe.repository.RecipeIngredientRepository;
import com.or1is1.hometender.api.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
	private final RecipeRepository recipeRepository;
	private final RecipeIngredientRepository recipeIngredientRepository;

	@Transactional
	public void post(Long loginId, PostRecipeRequest postRequest) {
		Recipe recipe = new Recipe(
				new Member(loginId),
				postRequest.name(),
				postRequest.description(),
				postRequest.craftMethod(),
				postRequest.manual()
		);

		recipeRepository.save(recipe);

		List<RecipeIngredientDto> recipeIngredientDtoList = postRequest.recipeIngredientList();

		recipeIngredientDtoList.forEach(
				recipeIngredientDto ->
						recipeIngredientRepository.save(
								new RecipeIngredient(
										recipe,
										new Ingredient(recipeIngredientDto.ingredientId()),
										recipeIngredientDto.size(),
										recipeIngredientDto.sizeType(),
										recipeIngredientDto.isOptional()
								)
						)
		);
	}

	public GetRecipeDetailResponse get(String name, Long loginId) {
		Recipe recipe = recipeRepository.findByWriterAndName(new Member(loginId), name)
				.orElseThrow(IngredientCanNotFindException::new);

		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipe(recipe);

		return new GetRecipeDetailResponse(recipe, recipeIngredientList);
	}

	public List<GetRecipeListResponse> getList(Long loginId) {

		return recipeRepository.findByWriter(new Member(loginId))
				.stream().map(GetRecipeListResponse::new)
				.toList();
	}

	@Transactional
	public void put(String name, Long loginId, PutRecipeRequest putRecipeRequest) {
		Recipe recipe = recipeRepository.findByWriterAndName(new Member(loginId), name)
				.orElseThrow(IngredientCanNotFindException::new);

		recipe.put(
				name,
				putRecipeRequest.description(),
				putRecipeRequest.craftMethod(),
				putRecipeRequest.manual()
		);
	}

	@Transactional
	public void delete(String name, Long loginId) {
		recipeRepository.deleteByWriterAndName(new Member(loginId), name);
	}
}
