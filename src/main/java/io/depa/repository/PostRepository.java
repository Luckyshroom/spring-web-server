package io.depa.repository;

import io.depa.model.Category;
import io.depa.model.Post;
import io.depa.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Boolean existsByTitle(String title);

    Page<Post> findByCategory(Category category, Pageable pageable);

    Optional<Post> findByUrlName(String urlName);

    Page<Post> findByTags(Tag tag, Pageable pageable);
}