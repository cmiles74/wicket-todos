package com.windsorsolutions.todos;

import javax.persistence.*;
import org.springframework.stereotype.*;
import java.util.List;

@Repository(value="ToDoDao")
public class ToDoJpaDao implements ToDoDao {

    @PersistenceContext
    private EntityManager entityManager = null;

    public ToDo persist(ToDo todo) {
	entityManager.persist(todo);
	return(todo);
    }

    public void remove(ToDo todo) {
	entityManager.remove(todo);
    }

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
