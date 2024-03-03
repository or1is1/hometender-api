package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.request.IngredientPostRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.request.IngredientPutRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.response.IngredientGetResponse;
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
	public CommonResponse<Void> postIngredient(@Validated @RequestBody IngredientPostRequest ingredientPostRequest,
	                                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.post(loginId, ingredientPostRequest);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<IngredientGetResponse>> getIngredientList(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		List<IngredientGetResponse> ingredientGetResponseList = ingredientService.getList(loginId);

		return new CommonResponse<>(null, ingredientGetResponseList);
	}

	@GetMapping("/{ingredientId}")
	public CommonResponse<IngredientGetResponse> getIngredient(@PathVariable Long ingredientId,
	                                                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		IngredientGetResponse ingredientGetResponse = ingredientService.get(ingredientId, loginId);

		return new CommonResponse<>(null, ingredientGetResponse);
	}

	@PutMapping("/{ingredientId}")
	public CommonResponse<Void> putIngredient(@PathVariable Long ingredientId,
	                                          @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId,
	                                          @Validated @RequestBody IngredientPutRequest ingredientPutRequest) {

		ingredientService.put(ingredientId, loginId, ingredientPutRequest);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{ingredientId}")
	public CommonResponse<Void> deleteIngredient(@PathVariable Long ingredientId,
	                                             @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.delete(ingredientId, loginId);

		return new CommonResponse<>(null, null);
	}
}
