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

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ActiveProfiles("test")
@Transactional
public class ContextDaoTest
    extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ContextDao contextDao = null;

    @Test
    public void testCreateAndRemove() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextDao.persist(context);
	Assert.assertNotNull(context.getId());

	// remove a context
	contextDao.remove(context);
	context = contextDao.get(context.getId());
	Assert.assertNull(context);
    }

    @Test
    public void testGetAll() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextDao.persist(context);
	Assert.assertNotNull(context.getId());

	// get all contexts
	List contexts = contextDao.getAll();
	Assert.assertEquals(contexts.size(), 1);

	// verify contexts
	Context contextListed = (Context) contexts.get(0);
	Assert.assertEquals(contextListed.getId(), context.getId());
    }

    @Test
    public void testGet() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextDao.persist(context);

	// fetch the context
	Context contextFetched = contextDao.get(context.getId());
	Assert.assertNotNull(contextFetched);

	// remove the context
	contextDao.remove(context);
    }

    @Test
    public void testMerge() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextDao.persist(context);

	// update the context
	context.setName("Home");
	context = contextDao.merge(context);
	Assert.assertEquals(context.getName(), "Home");

	// remove the context
	contextDao.remove(context);
    }
}
