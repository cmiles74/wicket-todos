package com.windsorsolutions.todos.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;

/**
 * Provides a repository for ToDo domain objects.
 */
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    public Iterable findByContext(Context context);
}
