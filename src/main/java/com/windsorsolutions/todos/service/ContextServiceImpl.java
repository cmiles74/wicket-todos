package com.windsorsolutions.todos.service;

import java.util.List;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.windsorsolutions.todos.dao.ContextRepository;
import com.windsorsolutions.todos.entities.Context;

/**
 * Provides a service for managing Context entities.
 */
@Named
@Transactional
public class ContextServiceImpl implements ContextService {

    @Autowired
    public ContextRepository repository;

    /**
     * Creates a new ContextService instance.
     */
    public ContextServiceImpl() {

    }

    /**
     * Persists the provided Context and returns a reference to the
     * new, persistent instance.
     *
     * @param Context Instance to be persisted
     * @return Reference to the new, persistent instance
     */
    public Context save(Context context) {

	context = repository.save(context);
	return(context);
    }

    /**
     * Removes the provided instance from the persistent store.
     *
     * @param Context Instance to be removed
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
     * Returns a List of all Context instances.
     *
     * @return A List of Context instances
     */
    public List findAll() {
	return(repository.findAll());
    }
}
