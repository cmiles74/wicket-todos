package com.windsorsolutions.todos.dao;

import javax.persistence.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.util.List;
import com.windsorsolutions.todos.entities.ToDo;
import com.windsorsolutions.todos.entities.Context;

@Repository(value="ToDoDao")
public class ToDoJpaDao implements ToDoDao {

    @PersistenceContext
    private EntityManager entityManager = null;

    @Transactional
    public ToDo persist(ToDo todo) {
	entityManager.persist(todo);
	return(todo);
    }

    @Transactional
    public void remove(ToDo todo) {
	ToDo todoRemove = entityManager.find(ToDo.class, todo.getId());
	entityManager.remove(todoRemove);
    }

    @Transactional
    public ToDo merge(ToDo todo) {
	ToDo todoMerged = entityManager.merge(todo);
	return(todoMerged);
    }

    public ToDo get(Long id) {
	ToDo todo = entityManager.find(ToDo.class, id);
	return(todo);
    }

    public List getAll() {
	Query query = entityManager.createQuery("select td from ToDo as td");
	return(query.getResultList());
    }

    public List getWithContext(Context context) {
	Query query =
	    entityManager.createQuery("select td from ToDo as td"
				      + " where td.context = ?1");
	query.setParameter(1, context);
	return(query.getResultList());
    }
}
