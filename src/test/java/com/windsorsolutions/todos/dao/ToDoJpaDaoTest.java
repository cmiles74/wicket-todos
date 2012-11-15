package com.windsorsolutions.todos.dao;

import org.junit.*;
import org.springframework.test.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ActiveProfiles("test")
@Transactional
public class ToDoJpaDaoTest
    extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ToDoJpaDao todoJpaDao = null;

    @Autowired
    ContextJpaDao contextJpaDao = null;

    @Test
    public void testCreateAndRemove() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = todoJpaDao.persist(todo);
	Assert.assertNotNull(todo.getId());

	// remove context and todo
	todoJpaDao.remove(todo);
	contextJpaDao.remove(context);
	todo = todoJpaDao.get(todo.getId());
	Assert.assertNull(todo);
    }

    @Test
    public void testGetAll() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = todoJpaDao.persist(todo);

	// get all todos
	List todos = todoJpaDao.getAll();
	Assert.assertEquals(todos.size(), 1);

	// verify todos
	ToDo todoListed = (ToDo) todos.get(0);
	Assert.assertEquals(todoListed.getId(), todo.getId());

	// remove todo and context
	todoJpaDao.remove(todo);
	contextJpaDao.remove(context);
    }

    @Test
    public void testMerge() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = todoJpaDao.persist(todo);

	// update the todo
	todo.setContent("I mean, like, code it up now!");
	todo = todoJpaDao.persist(todo);
	Assert.assertEquals(todo.getContent(), "I mean, like, code it up now!");

	// remove the todo and context
	todoJpaDao.remove(todo);
	contextJpaDao.remove(context);
    }

    @Test
    public void testGetWithContext() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = todoJpaDao.persist(todo);

	// get all todos
	List todos = todoJpaDao.getWithContext(context);
	Assert.assertEquals(todos.size(), 1);

	// verify todos
	ToDo todoListed = (ToDo) todos.get(0);
	Assert.assertEquals(todoListed.getId(), todo.getId());

	// remove todo and context
	todoJpaDao.remove(todo);
	contextJpaDao.remove(context);
    }
}
