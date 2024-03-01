package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.request.IngredientPostRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.request.IngredientPutRequest;
import com.or1is1.hometender.api.domain.ingredient.dto.response.IngredientGetResponse;
import jakarta.validation.constraints.NotBlank;
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

	@GetMapping("/{name}")
	public CommonResponse<IngredientGetResponse> getIngredient(@PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		IngredientGetResponse ingredientGetResponse = ingredientService.get(name, loginId);

		return new CommonResponse<>(null, ingredientGetResponse);
	}

	@PutMapping("/{name}")
	public CommonResponse<Void> patchIngredient(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId,
	                                            @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                            @Validated @RequestBody IngredientPutRequest ingredientPutRequest) {

		ingredientService.put(name, loginId, ingredientPutRequest);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{name}")
	public CommonResponse<Void> deleteIngredient(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId,
	                                             @PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name) {
		ingredientService.delete(name, loginId);

		return new CommonResponse<>(null, null);
	}
}
