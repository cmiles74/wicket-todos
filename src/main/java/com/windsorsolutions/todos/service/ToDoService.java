package com.windsorsolutions.todos.service;

import java.util.List;

import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;

/**
 * Provides an interface for managing ToDo entities.
 */
public interface ToDoService {

    /**
     * Persists the provided ToDo and returns a reference to the new,
     * persistent instance.
     *
     * @param ToDo Instance to be persisted
     * @return Reference to the new, perisitent instance.
     */
    public ToDo save(ToDo todo);

    /**
     * Removes the provided instance from the persistent store.
     *
     * @param ToDo Instance to be removed
     */
    public void delete(ToDo todo);

    /**
     * Returns the instance with the matching unique ID.
     *
     * @param id Unique ID of the instance to fetch
     * @return ToDo instance with the matching unique ID
     */
    public ToDo find(Long id);

    /**
     * Returns an Iterable of all ToDo instances.
     *
     * @return an Iterable of ToDo instances.
     */
    public List findAll();

    /**
     * Returns an Iterable of all ToDo instances related to
     * the provided Context instance.
     *
     * @param Context Instance used for matching
     * @return an Iterable of ToDo instances
     */
    public List findByContext(Context context);
}
