package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.PostAndPutIngredientRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.GetIngredientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
public class IngredientController {
	private final IngredientService ingredientService;

	@PostMapping
	public CommonResponse<Void> postIngredient(@Validated @RequestBody PostAndPutIngredientRequest postAndPutIngredientRequest,
	                                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.post(loginId, postAndPutIngredientRequest);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<GetIngredientResponse>> getIngredientList(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		List<GetIngredientResponse> getIngredientResponseList = ingredientService.getList(loginId);

		return new CommonResponse<>(null, getIngredientResponseList);
	}

	@GetMapping("/{ingredientId}")
	public CommonResponse<GetIngredientResponse> getIngredient(@PathVariable Long ingredientId,
	                                                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		GetIngredientResponse getIngredientResponse = ingredientService.get(ingredientId, loginId);

		return new CommonResponse<>(null, getIngredientResponse);
	}

	@PutMapping("/{ingredientId}")
	public CommonResponse<Void> putIngredient(@PathVariable Long ingredientId,
	                                          @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId,
	                                          @Validated @RequestBody PostAndPutIngredientRequest postAndPutIngredientRequest) {

		ingredientService.put(ingredientId, loginId, postAndPutIngredientRequest);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{ingredientId}")
	public CommonResponse<Void> deleteIngredient(@PathVariable Long ingredientId,
	                                             @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.delete(ingredientId, loginId);

		return new CommonResponse<>(null, null);
	}
}
