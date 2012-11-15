package com.windsorsolutions.todos.dao;

import java.util.List;
import com.windsorsolutions.todos.entities.Context;

/**
 * Provides a data access object for managing Context entities.
 */
public interface ContextDao {

    /**
     * Stores the provided Context in the data source. This should
     * only be used for brand new Context entities.
     *
     * @param context The context to be persisted
     * @return A reference to the new, persistent context
     */
    public Context persist(Context context);

    /**
     * Removes the provided Context entity from the data store.
     *
     * @param context The context to be removed
     */
    public void remove(Context context);

    /**
     * Merges the data in the provided Context entity with the data
     * stored in the data source.
     *
     * @param context The context to merge with the data source
     * @return A reference to the new, updated persisten context
     */
    public Context merge(Context context);

    /**
     * Returns the context entity from the data source with the
     * matching unique identifier. If no context is found with the
     * provided identifier then "null" is returned.
     *
     * @param id Unique identifier of the context to retrieve
     * @return A reference to the found context or null
     */
    public Context get(Long id);

    /**
     * Returns a List of all the context entities contained in the
     * data source.
     *
     * @return A list of all the context entities in the data source
     */
    public List getAll();
}
