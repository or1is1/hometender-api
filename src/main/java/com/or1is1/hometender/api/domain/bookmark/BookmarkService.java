package com.or1is1.hometender.api.domain.bookmark;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import com.or1is1.hometender.api.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

	private final BookmarkRepository bookmarkRepository;

	@Transactional
	public void post(Long recipeId, Long loginId) {

		bookmarkRepository.save(new Bookmark(new Member(loginId), new Recipe(recipeId)));
	}

	public List<RecipeDto> getList(Long loginId) {

		return bookmarkRepository.findByWriter(new Member(loginId))
				.stream().map(RecipeDto::new)
				.toList();
	}

	@Transactional
	public void delete(Long recipeId, Long loginId) {

		bookmarkRepository.deleteByWriterAndRecipe(new Member(loginId), new Recipe(recipeId));
	}
}
