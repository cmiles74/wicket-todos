package com.windsorsolutions.todos.entities;

import javax.persistence.*;
import java.util.Collection;
import java.io.Serializable;

/**
 * Provides an object that models a context for a to-do item. A
 * context represents a place or situation in which a to-do item may
 * be completed (i.e. "home", "office", etc.)
 */
@Entity
@Table(name="contexts")
public class Context implements Serializable {

    /** Unique ID */
    @Id
    @GeneratedValue
    private Long id = null;

    /** Name of our context */
    private String name = null;

    /** A brief description for our context */
    private String description = null;

    /** To-do items for this context */
    @OneToMany(cascade=CascadeType.ALL, mappedBy="context")
    private Collection<ToDo> todos;

    /**
     * Creates a new context object.
     */
    public Context() {

    }

    // accessor and mutator methods

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return(id);
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return(name);
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return(description);
    }

    public Collection<ToDo> getTodos() {
	return(todos);
    }
}
