package com.windsorsolutions.todos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;

/**
 * Provides a repository for ToDo domain objects.
 */
@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    public List findByContext(Context context);
}
