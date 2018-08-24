package io.depa.repository;

import io.depa.model.Context;
import io.depa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContextRepository extends JpaRepository<Context, Integer> {
    Context findByTag(Tag tag);
}
