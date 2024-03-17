package com.or1is1.hometender.api.domain.bookmark;

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
	public void post(@PathVariable Long recipeId,
	                 @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		bookmarkService.post(recipeId, loginId);
	}

	@GetMapping
	public List<RecipeDto> get(@SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		return bookmarkService.getList(loginId);
	}

	@DeleteMapping("/{recipeId}")
	public void delete(@PathVariable Long recipeId,
	                   @SessionAttribute(StringConst.LOGIN_MEMBER) Long loginId) {

		bookmarkService.delete(recipeId, loginId);
	}
}
