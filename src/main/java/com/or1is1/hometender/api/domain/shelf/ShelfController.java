package com.or1is1.hometender.api.domain.shelf;

import com.or1is1.hometender.api.common.StringConst;
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
	public void post(@PathVariable Long ingredientId,
	                 @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		shelfService.post(ingredientId, loginId);
	}

	@GetMapping
	public List<IngredientDto> get(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		return shelfService.getList(loginId);
	}

	@DeleteMapping("/{ingredientId}")
	public void delete(@PathVariable Long ingredientId,
	                   @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		shelfService.delete(ingredientId, loginId);
	}
}
