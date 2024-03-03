package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.ingredient.Ingredient;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientCanNotFindException;
import com.or1is1.hometender.api.domain.ingredient.exception.IngredientIsNotMineException;
import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeIngredientDto;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeDto;
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
	public void post(Long loginId, RecipeDto postRequest) {

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

	public RecipeDto get(Long recipeId, Long loginId) {

		Recipe recipe = recipeRepository.findByRecipeIdAndWriter(recipeId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findByRecipe(recipe);

		return new RecipeDto(recipe, recipeIngredientList);
	}

	@Transactional
	public void put(Long recipeId, Long loginId, RecipeDto recipeDto) {

		Recipe recipe = recipeRepository.findByRecipeIdAndWriter(recipeId, new Member(loginId))
				.orElseThrow(IngredientCanNotFindException::new);

		if (!loginId.equals(recipe.getWriter().getId())) {
			throw new IngredientIsNotMineException();
		}

		recipe.put(
				recipeDto.name(),
				recipeDto.description(),
				recipeDto.craftMethod(),
				recipeDto.manual()
		);

		List<RecipeIngredientDto> recipeIngredientDtoList = recipeDto.recipeIngredientList();
	}

	@Transactional
	public void delete(Long recipeId, Long loginId) {

		recipeRepository.deleteByRecipeIdAndWriter(recipeId, new Member(loginId));
	}
}
