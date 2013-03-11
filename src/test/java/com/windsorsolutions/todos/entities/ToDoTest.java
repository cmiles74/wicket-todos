package com.windsorsolutions.todos.entities;

import org.junit.*;

import java.util.Date;

public class ToDoTest {

    private Context context = null;
    private ToDo todo = null;
    private Date completedAtDate = new Date();

    @Before
    public void setUp() {

        context = new Context();
        context.setId(new Long(101));

        todo = new ToDo();
        todo.setId(new Long(201));
        todo.setContext(context);
        todo.setContent("This is my todo item.");
        todo.setCompletedAt(completedAtDate);
    }

    @Test
    public void testId() {

        Long expected = new Long(201);
        Long result = todo.getId();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testContext() {

        Context expected = context;
        Context result = todo.getContext();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testContent() {

        String expected = "This is my todo item.";
        String result = todo.getContent();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testCompletedAt() {

        Date expected = completedAtDate;
        Date result = todo.getCompletedAt();

        Assert.assertEquals(expected, result);
    }
}
