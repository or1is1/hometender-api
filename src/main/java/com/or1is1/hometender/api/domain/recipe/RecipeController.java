package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.domain.recipe.dto.GetRecipeListResponse;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.or1is1.hometender.api.StringConst.LOGIN_MEMBER;
import static com.or1is1.hometender.api.domain.recipe.exception.RecipeIngredientIsEmptyException.RECIPE_INGREDIENT_IS_EMPTY_EXCEPTION;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {

	private final RecipeService recipeService;

	@PostMapping
	public void postRecipe(@Validated @RequestBody RecipeDto recipeDto,
	                       @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		if (recipeDto.recipeIngredientList() == null) {
			throw RECIPE_INGREDIENT_IS_EMPTY_EXCEPTION;
		}

		recipeService.post(memberId, recipeDto);
	}

	@GetMapping
	public List<GetRecipeListResponse> getRecipeList(@SessionAttribute(LOGIN_MEMBER) Long memberId) {

		return recipeService.getList(memberId);
	}

	@GetMapping("/{recipeId}")
	public RecipeDto getRecipeDetail(@PathVariable Long recipeId,
	                                 @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		return recipeService.get(recipeId, memberId);
	}

	@PutMapping("/{recipeId}")
	public void putRecipe(@PathVariable Long recipeId,
	                      @SessionAttribute(LOGIN_MEMBER) Long memberId,
	                      @Validated @RequestBody RecipeDto recipeDto) {

		recipeService.put(recipeId, memberId, recipeDto);
	}

	@DeleteMapping("/{recipeId}")
	public void deleteRecipe(@PathVariable Long recipeId,
	                         @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		recipeService.delete(recipeId, memberId);
	}
}
