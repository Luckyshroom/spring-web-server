package io.depa.repository;

import io.depa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Boolean existsByName(String name);

    Tag findByName(String name);
}
