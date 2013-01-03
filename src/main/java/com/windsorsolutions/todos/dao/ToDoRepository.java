package com.windsorsolutions.todos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides a repository for ToDo domain objects.
 */
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findByContext(Context context);
}
