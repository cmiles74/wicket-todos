package com.windsorsolutions.todos.service;

import com.windsorsolutions.todos.entities.Context;

/**
 * Provides an interface for managing Context entities.
 */
public interface ContextService {

    /**
     * Persists the provided Context and returns a reference to the
     * new, persistent instance.
     *
     * @param context Instance to be persisted
     * @return Reference to the new, persistent instance
     */
    public Context save(Context context);

    /**
     * Removes the provided instance from the persistent store.
     *
     * @param context Instance to be removed
     */
    public void delete(Context context);

    /**
     * Returns the instance with the matching unique ID.
     *
     * @param id Unique ID of the instance to fetch
     * @return Context instance with the matching unique ID
     */
    public Context find(Long id);

    /**
     * Returns an Iterable of all Context instances.
     *
     * @return An Iterable of Context instances
     */
    public Iterable findAll();
}
