package com.windsorsolutions.todos;

import java.util.List;

public interface ToDoDao {

    /**
     * Stores the provided todo entity in the data source. This should
     * only be used for brand new todo entities.
     *
     * @param todo The todo to be persisted
     * @return A reference to the new, persisted todo
     */
    public ToDo persist(ToDo todo);

    /**
     * Removes the provided todo entity from the data store.
     *
     * @param todo The todo item to be remove
     */
    public void remove(ToDo todo);

    /**
     * Merges the data in the provided todo with the data stored in
     * the data source.
     *
     * @param todo The todo to merge with the data source
     * @return A reference to the new, updated todo
     */
    public ToDo merge(ToDo todo);

    /**
     * Returns the todo entity from the data source with the matching
     * unique identifier. If no todo is found with the provided
     * identifier then "null" is returned.
     *
     * @param id Unique identifier of the todo to retrieve
     * @return A reference to the found todo or null
     */
    public ToDo get(Long id);

    /**
     * Returns a List of all the todo entities contained in the data
     * source.
     *
     * @return A list of all the todo entities in the data source
     */
    public List getAll();

    /**
     * Returns a List of all the todo entities contained in the data
     * source that are linked to the provided context.
     *
     * @return A list of all the todo entities with the provided
     * context
     */
    public List getWithContext(Context context);
}
