package com.windsorsolutions.todos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.windsorsolutions.todos.entities.Context;
import org.springframework.stereotype.Repository;

/**
 * Provides a repository for Context domain objects.
 */
public interface ContextRepository extends JpaRepository<Context, Long> {

}
