package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientAddRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
