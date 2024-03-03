package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientIsNotMineException;
import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;
import com.or1is1.hometender.api.domain.recipe.dto.PostRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.PutRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.GetRecipeDetailResponse;
import com.or1is1.hometender.api.domain.recipe.dto.GetRecipeListResponse;
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

	public List<GetRecipeListResponse> getList(Long loginId) {

		return recipeRepository.findByWriter(new Member(loginId))
				.stream().map(GetRecipeListResponse::new)
				.toList();
	}

	public GetRecipeDetailResponse get(Long recipeId, Long loginId) {

		Recipe recipe = recipeRepository.findByRecipeIdAndWriter(recipeId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipe(recipe);

		return new GetRecipeDetailResponse(recipe, recipeIngredientList);
	}

	@Transactional
	public void put(Long recipeId, Long loginId, PutRecipeRequest putRecipeRequest) {

		Recipe recipe = recipeRepository.findByRecipeIdAndWriter(recipeId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		if (!loginId.equals(recipe.getWriter().getId())) {
			throw new IngredientIsNotMineException();
		}

		recipe.put(
				putRecipeRequest.name(),
				putRecipeRequest.description(),
				putRecipeRequest.craftMethod(),
				putRecipeRequest.manual()
		);
	}

	@Transactional
	public void delete(Long recipeId, Long loginId) {

		recipeRepository.deleteByRecipeIdAndWriter(recipeId, new Member(loginId));
	}
}
