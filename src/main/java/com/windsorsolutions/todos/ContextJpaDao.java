package com.windsorsolutions.todos;

import javax.persistence.*;
import org.springframework.stereotype.*;
import java.util.List;

@Repository(value="ContextDao")
public class ContextJpaDao implements ContextDao{

    @PersistenceContext
    private EntityManager entityManager = null;

    public Context persistContext(Context context) {
	entityManager.persist(context);
	return(context);
    }

    public void removeContext(Context context) {
	entityManager.remove(context);
    }

    public Context mergeContext(Context context) {
	Context contextMerged = entityManager.merge(context);
	return(contextMerged);
    }

    public Context getContext(Long id) {
	Context todo = entityManager.find(Context.class, id);
	return(todo);
    }

    public List getContexts() {
	Query query = entityManager.createQuery("select c from Context");
	return(query.getResultList());
    }
}
