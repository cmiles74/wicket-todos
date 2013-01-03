package com.windsorsolutions.todos.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.windsorsolutions.todos.dao.ContextRepository;
import com.windsorsolutions.todos.entities.Context;
import org.springframework.stereotype.Service;

/**
 * Provides a service for managing Context entities.
 */
@Service
public class ContextService {

    @Autowired
    public ContextRepository repository;

    /**
     * Creates a new ContextService instance.
     */
    public ContextService() {

    }

    /**
     * Persists the provided Context and returns a reference to the
     * new, persistent instance.
     *
     * @param context Instance to be persisted
     * @return Reference to the new, persistent instance
     */
    public Context save(Context context) {

	context = repository.save(context);
	return(context);
    }

    /**
     * Removes the provided instance from the persistent store.
     *
     * @param context Instance to be removed
     */
    public void delete(Context context) {

	repository.delete(context.getId());
    }

    /**
     * Returns the instance with the matching unique ID.
     *
     * @param id Unique ID of the instance to fetch
     * @return Context instance with the matching unique ID
     */
    public Context find(Long id) {
	return(repository.findOne(id));
    }

    /**
     * Returns an Iterable of all Context instances.
     *
     * @return An Iterable of Context instances
     */
    public Iterable findAll() {
	return(repository.findAll());
    }
}
