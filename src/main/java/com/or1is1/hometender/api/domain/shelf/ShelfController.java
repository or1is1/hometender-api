package com.or1is1.hometender.api.domain.shelf;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.ingredient.dto.IngredientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelf")
@RequiredArgsConstructor
public class ShelfController {

	private final ShelfService shelfService;

	@PostMapping("/{ingredientId}")
	public CommonResponse<Void> post(@PathVariable Long ingredientId,
	                                 @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		shelfService.post(ingredientId, loginId);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<IngredientDto>> get(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		List<IngredientDto> ingredientDtoList = shelfService.getList(loginId);

		return new CommonResponse<>(null, ingredientDtoList);
	}

	@DeleteMapping("/{ingredientId}")
	public CommonResponse<Void> delete(@PathVariable Long ingredientId,
	                                   @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		shelfService.delete(ingredientId, loginId);

		return new CommonResponse<>(null, null);
	}
}
