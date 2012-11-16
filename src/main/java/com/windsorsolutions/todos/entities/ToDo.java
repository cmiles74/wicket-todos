package com.windsorsolutions.todos.entities;

import java.util.Date;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Provides an object that models a To-Do item.
 */
@Entity
@Table(name="todos")
public class ToDo implements Serializable {

    /** Unique ID */
    @Id
    @GeneratedValue
    private Long id = null;

    /** Context ID */
    @ManyToOne(optional=false)
    private Context context = null;

    /** Content of the To-Do item */
    private String content = null;

    /** Date of completion */
    private Date completedAt = null;

    /**
     * Creates a new To-Do item.
     */
    public ToDo() {

    }

    // accessor and mutator methods

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return(id);
    }

    public void setContext(Context context) {
	this.context = context;
    }

    public Context getContext() {
	return(context);
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getContent() {
	return(content);
    }

    public void setCompletedAt(Date completedAt) {
	this.completedAt = completedAt;
    }

    public Date getCompletedAt() {
	return(completedAt);
    }
}
