package com.windsorsolutions.todos;

import javax.persistence.*;
import org.springframework.stereotype.*;
import java.util.List;

@Repository(value="ToDoDao")
public class ToDoJpaDao implements ToDoDao{

    @PersistenceContext
    private EntityManager entityManager = null;

    public ToDo getToDo(Long id) {
	ToDo todo = entityManager.find(ToDo.class, id);
	return(todo);
    }

    public List getToDos() {
	Query query = entityManager.createQuery("select td from ToDo");
	return(query.getResultList());
    }
}
