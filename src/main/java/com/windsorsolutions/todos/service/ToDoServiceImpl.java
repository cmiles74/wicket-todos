package com.windsorsolutions.todos.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.windsorsolutions.todos.dao.ToDoRepository;
import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;

/**
 * Provides a service for managing ToDo entities.
 */
@Named
@Transactional
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    public ToDoRepository repository;

    /**
     * Creates a new ToDoService instance.
     */
    public ToDoServiceImpl() {

    }

    /**
     * Persists the provided ToDo and returns a reference to the new,
     * persistent instance.
     *
     * @param ToDo Instance to be persisted
     * @return Reference to the new, perisitent instance.
     */
    public ToDo save(ToDo todo) {

	todo = repository.save(todo);
	return(todo);
    }

    /**
     * Removes the provided instance from the persistent store.
     *
     * @param ToDo Instance to be removed
     */
    public void delete(ToDo todo) {

	repository.delete(todo.getId());
    }

    /**
     * Returns the instance with the matching unique ID.
     *
     * @param id Unique ID of the instance to fetch
     * @return ToDo instance with the matching unique ID
     */
    public ToDo find(Long id) {
	return(repository.findOne(id));
    }

    /**
     * Returns an List of all ToDo instances.
     *
     * @return a List of ToDo instances.
     */
    public List findAll() {
	return(repository.findAll());
    }

    /**
     * Returns an List of all ToDo instances related to
     * the provided Context instance.
     *
     * @param Context Instance used for matching
     * @return a List of ToDo instances
     */
    public List findByContext(Context context) {
	return(repository.findByContext(context));
    }
}
