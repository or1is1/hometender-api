package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
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
	public CommonResponse<Void> postIngredient(@Validated @RequestBody IngredientDto ingredientDto,
	                                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.post(loginId, ingredientDto);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<IngredientDto>> getIngredientList(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		List<IngredientDto> ingredientDtoList = ingredientService.getList(loginId);

		return new CommonResponse<>(null, ingredientDtoList);
	}

	@GetMapping("/{ingredientId}")
	public CommonResponse<IngredientDto> getIngredient(@PathVariable Long ingredientId,
	                                                   @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		IngredientDto ingredientDto = ingredientService.get(ingredientId, loginId);

		return new CommonResponse<>(null, ingredientDto);
	}

	@PutMapping("/{ingredientId}")
	public CommonResponse<Void> putIngredient(@PathVariable Long ingredientId,
	                                          @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId,
	                                          @Validated @RequestBody IngredientDto ingredientDto) {

		ingredientService.put(ingredientId, loginId, ingredientDto);

		return new CommonResponse<>(null, null);
	}

	@DeleteMapping("/{ingredientId}")
	public CommonResponse<Void> deleteIngredient(@PathVariable Long ingredientId,
	                                             @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.delete(ingredientId, loginId);

		return new CommonResponse<>(null, null);
	}
}
