package com.windsorsolutions.todos.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import com.windsorsolutions.todos.entities.Context;
import com.windsorsolutions.todos.entities.ToDo;
import com.windsorsolutions.todos.service.ContextService;
import com.windsorsolutions.todos.service.ToDoService;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ActiveProfiles("test")
@Transactional
public class ToDoServiceTest
    extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private ContextService contextService;

    @Test
    public void testCreateAndRemove() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = toDoService.save(todo);
	Assert.assertNotNull(todo.getId());

	// remove context and todo
	toDoService.delete(todo);
	contextService.delete(context);
	todo = toDoService.find(todo.getId());
	Assert.assertNull(todo);
    }

    @Test
    public void testGetAll() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = toDoService.save(todo);

	// get all todos
	Iterable iterable = toDoService.findAll();

	int count = 0;
	ToDo toDoListed = null;
	while(iterable.iterator().hasNext()) {

	    toDoListed = (ToDo) iterable.iterator().next();
	    count += 1;
	}

	Assert.assertEquals(count, 1);

	// verify todos
	Assert.assertEquals(toDoListed.getId(), todo.getId());

	// remove todo and context
	toDoService.delete(todo);
	contextService.delete(context);
    }

    @Test
    public void testMerge() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = toDoService.save(todo);

	// update the todo
	todo.setContent("I mean, like, code it up now!");
	todo = toDoService.save(todo);
	Assert.assertEquals(todo.getContent(), "I mean, like, code it up now!");

	// remove the todo and context
	toDoService.delete(todo);
	contextService.delete(context);
    }

    @Test
    public void testGetWithContext() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);

	// create a todo item
	ToDo todo = new ToDo();
	todo.setContext(context);
	todo.setContent("Code up the sample todo application.");
	todo = toDoService.save(todo);

	// get all todos
	Iterable iterable = toDoService.findByContext(context);

	int count = 0;
	ToDo toDoListed = null;
	while(iterable.iterator().hasNext()) {

	    toDoListed = (ToDo) iterable.iterator().next();
	    count += 1;
	}

	Assert.assertEquals(count, 1);

	// verify todos
	Assert.assertEquals(toDoListed.getId(), todo.getId());

	// remove todo and context
	toDoService.delete(todo);
	contextService.delete(context);
    }
}
