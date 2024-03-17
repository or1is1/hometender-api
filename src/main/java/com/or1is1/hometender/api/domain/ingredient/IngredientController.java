package com.or1is1.hometender.api.domain.ingredient;

import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

	private final IngredientService ingredientService;

	@PostMapping
	public void postIngredient(@Validated @RequestBody IngredientDto ingredientDto,
	                           @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.post(loginId, ingredientDto);
	}

	@GetMapping
	public List<IngredientDto> getIngredientList(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		return ingredientService.getList(loginId);
	}

	@GetMapping("/{ingredientId}")
	public IngredientDto getIngredient(@PathVariable Long ingredientId,
	                                   @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		return ingredientService.get(ingredientId, loginId);
	}

	@PutMapping("/{ingredientId}")
	public void putIngredient(@PathVariable Long ingredientId,
	                          @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId,
	                          @Validated @RequestBody IngredientDto ingredientDto) {

		ingredientService.put(ingredientId, loginId, ingredientDto);
	}

	@DeleteMapping("/{ingredientId}")
	public void deleteIngredient(@PathVariable Long ingredientId,
	                             @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		ingredientService.delete(ingredientId, loginId);
	}
}
