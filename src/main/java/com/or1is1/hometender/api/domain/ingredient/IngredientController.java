package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientAddRequest;
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
	public CommonResponse<Void> addIngredient(@Validated @RequestBody IngredientAddRequest ingredientAddRequest,
	                                          @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {
		ingredientService.add(loginId, ingredientAddRequest);

		return new CommonResponse<>(null, null);
	}

	@GetMapping("{name}")
	public CommonResponse<Ingredient> getIngredient(@PathVariable @NotBlank(message = "{validation.constraints.NotBlank}") String name,
	                                          @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		Ingredient ingredient = ingredientService.get(name, loginId);

		return new CommonResponse<>(null, ingredient);
	}

	@GetMapping
	public CommonResponse<List<Ingredient>> getIngredientList(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		List<Ingredient> list = ingredientService.getList(loginId);

		return new CommonResponse<>(null, list);
	}
}
