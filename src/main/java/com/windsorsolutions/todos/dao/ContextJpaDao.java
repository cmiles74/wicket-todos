package com.windsorsolutions.todos.dao;

import javax.persistence.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.util.List;
import com.windsorsolutions.todos.entities.Context;

@Repository(value="ContextDao")
public class ContextJpaDao implements ContextDao{

    @PersistenceContext
    private EntityManager entityManager = null;

    @Transactional
    public Context persist(Context context) {
	entityManager.persist(context);
	return(context);
    }

    @Transactional
    public void remove(Context context) {
	Context contextRemove = entityManager.find(Context.class, context.getId());
	entityManager.remove(contextRemove);
    }

    @Transactional
    public Context merge(Context context) {
	Context contextMerged = entityManager.merge(context);
	return(contextMerged);
    }

    public Context get(Long id) {
	Context todo = entityManager.find(Context.class, id);
	return(todo);
    }

    public List getAll() {
	Query query = entityManager.createQuery("select c from Context as c");
	return(query.getResultList());
    }
}
