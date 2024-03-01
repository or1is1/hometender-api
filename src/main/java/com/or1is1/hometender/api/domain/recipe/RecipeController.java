package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.recipe.dto.request.RecipePostRequest;
import com.or1is1.hometender.api.domain.recipe.dto.request.RecipePutRequest;
import com.or1is1.hometender.api.domain.recipe.dto.response.RecipeGetResponse;
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
	public CommonResponse<Void> postIngredient(@Validated @RequestBody RecipePostRequest recipePostRequest,
	                                           @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		recipeService.post(memberId, recipePostRequest);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<RecipeGetResponse>> getIngredientList(@SessionAttribute(LOGIN_MEMBER) Long memberId) {

		List<RecipeGetResponse> recipeGetResponseList = recipeService.getList(memberId);

		return new CommonResponse<>(null, recipeGetResponseList);
	}

	@GetMapping("/{name}")
	public CommonResponse<RecipeGetResponse> getIngredient(@PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                                       @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		RecipeGetResponse recipeGetResponse = recipeService.get(name, memberId);

		return new CommonResponse<>(null, recipeGetResponse);
	}

	@PutMapping("/{name}")
	public CommonResponse<Void> patchIngredient(@SessionAttribute(LOGIN_MEMBER) Long memberId,
	                                            @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                            @Validated @RequestBody RecipePutRequest recipePutRequest) {

		recipeService.put(name, memberId, recipePutRequest);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{name}")
	public CommonResponse<Void> deleteIngredient(@SessionAttribute(LOGIN_MEMBER) Long memberId,
	                                             @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name) {
		recipeService.delete(name, memberId);

		return new CommonResponse<>(null, null);
	}
}
