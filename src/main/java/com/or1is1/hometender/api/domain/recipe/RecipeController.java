package com.or1is1.hometender.api.domain.recipe;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.domain.recipe.dto.request.PostRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.request.PutRecipeRequest;
import com.or1is1.hometender.api.domain.recipe.dto.response.GetRecipeResponse;
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
	public CommonResponse<Void> postIngredient(@Validated @RequestBody PostRecipeRequest postRecipeRequest,
	                                           @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		recipeService.post(memberId, postRecipeRequest);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<GetRecipeResponse>> getIngredientList(@SessionAttribute(LOGIN_MEMBER) Long memberId) {

		List<GetRecipeResponse> getRecipeResponseList = recipeService.getList(memberId);

		return new CommonResponse<>(null, getRecipeResponseList);
	}

	@GetMapping("/{name}")
	public CommonResponse<GetRecipeResponse> getIngredient(@PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                                       @SessionAttribute(LOGIN_MEMBER) Long memberId) {

		GetRecipeResponse getRecipeResponse = recipeService.get(name, memberId);

		return new CommonResponse<>(null, getRecipeResponse);
	}

	@PutMapping("/{name}")
	public CommonResponse<Void> patchIngredient(@SessionAttribute(LOGIN_MEMBER) Long memberId,
	                                            @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                            @Validated @RequestBody PutRecipeRequest putRecipeRequest) {

		recipeService.put(name, memberId, putRecipeRequest);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{name}")
	public CommonResponse<Void> deleteIngredient(@SessionAttribute(LOGIN_MEMBER) Long memberId,
	                                             @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name) {
		recipeService.delete(name, memberId);

		return new CommonResponse<>(null, null);
	}
}
