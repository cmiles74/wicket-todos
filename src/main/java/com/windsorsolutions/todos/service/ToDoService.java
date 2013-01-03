package com.windsorsolutions.todos.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.windsorsolutions.todos.dao.ToDoRepository;
import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Provides a service for managing ToDo entities.
 */
public interface ToDoService {

    /**
     * Persists the provided ToDo and returns a reference to the new,
     * persistent instance.
     *
     * @param todo Instance to be persisted
     * @return Reference to the new, persistent instance.
     */
    public ToDo save(ToDo todo);

    /**
     * Removes the provided instance from the persistent store.
     *
     * @param todo Instance to be removed
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
    public Iterable findAll();

    /**
     * Returns an Iterable of all ToDo instances related to
     * the provided Context instance.
     *
     * @param context Instance used for matching
     * @return an Iterable of ToDo instances
     */
    public Iterable findByContext(Context context);
}
