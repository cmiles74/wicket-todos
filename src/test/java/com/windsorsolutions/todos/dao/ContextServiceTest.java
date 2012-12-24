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
import com.windsorsolutions.todos.service.ContextService;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ActiveProfiles("test")
@Transactional
public class ContextServiceTest
    extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ContextService contextService;

    @Test
    public void testCreateAndRemove() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);
	Assert.assertNotNull(context.getId());

	// remove a context
	contextService.delete(context);
	context = contextService.find(context.getId());
	Assert.assertNull(context);
    }

    @Test
    public void testGetAll() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);
	Assert.assertNotNull(context.getId());

	// get all contexts
	Iterable iterable = contextService.findAll();

	int count = 0;
	Context contextListed = null;
	while(iterable.iterator().hasNext()) {
	    contextListed = (Context) iterable.iterator().next();
	    count += 1;
	}

	Assert.assertEquals(count, 1);

	// verify context
	Assert.assertEquals(contextListed.getId(), context.getId());
    }

    @Test
    public void testGet() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);

	// fetch the context
	Context contextFetched = contextService.find(context.getId());
	Assert.assertNotNull(contextFetched);

	// remove the context
	contextService.delete(context);
    }

    @Test
    public void testMerge() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextService.save(context);

	// update the context
	context.setName("Home");
	context = contextService.save(context);
	Assert.assertEquals(context.getName(), "Home");

	// remove the context
	contextService.delete(context);
    }
}
