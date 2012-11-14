package com.windsorsolutions.todos;

import org.junit.*;

public class ContextTest {

    private Context context = null;

    @Before
    public void setUp() {

	context = new Context();
	context.setId(new Long(101));
	context.setName("Office");
	context.setDescription("Wherever my laptop sits.");
    }

    @Test
    public void testId() {

	Long expected = new Long(101);
	Long result = context.getId();
	Assert.assertEquals(expected, result);
    }

    @Test
    public void testName() {

	String expected = "Office";
	String result = context.getName();
	Assert.assertEquals(expected, result);
    }

    @Test
    public void testDescription() {

	String expected = "Wherever my laptop sits.";
	String result = context.getDescription();
	Assert.assertEquals(expected, result);
    }
}
