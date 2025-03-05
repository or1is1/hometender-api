package com.or1is1.hometender.api.domain.bookmark;

import com.or1is1.hometender.api.domain.member.Member;
import com.or1is1.hometender.api.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	List<Bookmark> findByWriter(Member writer);

	void deleteByWriterAndRecipe(Member writer, Recipe recipe);
}
