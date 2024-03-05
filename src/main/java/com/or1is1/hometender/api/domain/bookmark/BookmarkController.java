package com.or1is1.hometender.api.domain.bookmark;

import com.or1is1.hometender.api.CommonResponse;
import com.or1is1.hometender.api.StringConst;
import com.or1is1.hometender.api.domain.recipe.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@PostMapping("/{recipeId}")
	public CommonResponse<Void> post(@PathVariable Long recipeId,
	                                 @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		bookmarkService.post(recipeId, loginId);

		return new CommonResponse<>(null, null);
	}

	@GetMapping
	public CommonResponse<List<RecipeDto>> get(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		List<RecipeDto> ingredientDtoList = bookmarkService.getList(loginId);

		return new CommonResponse<>(null, ingredientDtoList);
	}

	@DeleteMapping("/{recipeId}")
	public CommonResponse<Void> delete(@PathVariable Long recipeId,
	                                   @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		bookmarkService.delete(recipeId, loginId);

		return new CommonResponse<>(null, null);
	}
}
