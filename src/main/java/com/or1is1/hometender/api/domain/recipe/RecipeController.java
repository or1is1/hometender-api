package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.recipe.dto.request.PostRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.request.PutRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.response.GetRecipeDetailResponse;
import com.or1is1.hometender.api.domain.recipe.dto.response.GetRecipeListResponse;
import com.or1is1.hometender.api.domain.recipe.exception.RecipeIngredientIsEmptyException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.or1is1.hometender.api.StringConst.LOGIN_MEMBER;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
public class RecipeController {
	private final RecipeService recipeService;

	@PostMapping
	public CommonResponse<Void> postRecipe(@Validated @RequestBody PostRecipeRequest postRecipeRequest,
	                                       @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		if (postRecipeRequest.recipeIngredientList() == null) {
			throw new RecipeIngredientIsEmptyException();
		}

		recipeService.post(memberId, postRecipeRequest);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<GetRecipeListResponse>> getRecipeList(@SessionAttribute(LOGIN_MEMBER) Long memberId) {

		List<GetRecipeListResponse> getRecipeListResponseList = recipeService.getList(memberId);

		return new CommonResponse<>(null, getRecipeListResponseList);
	}

	@GetMapping("/{name}")
	public CommonResponse<GetRecipeDetailResponse> getRecipeDetail(@PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                                               @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		GetRecipeDetailResponse getRecipeDetailResponse = recipeService.get(name, memberId);

		return new CommonResponse<>(null, getRecipeDetailResponse);
	}

	@PutMapping("/{name}")
	public CommonResponse<Void> patchRecipe(@SessionAttribute(LOGIN_MEMBER) Long memberId,
	                                        @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                        @Validated @RequestBody PutRecipeRequest putRecipeRequest) {

		recipeService.put(name, memberId, putRecipeRequest);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{name}")
	public CommonResponse<Void> deleteRecipe(@SessionAttribute(LOGIN_MEMBER) Long memberId,
	                                         @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name) {
		recipeService.delete(name, memberId);

		return new CommonResponse<>(null, null);
	}
}
