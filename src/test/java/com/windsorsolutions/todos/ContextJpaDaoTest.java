package com.windsorsolutions.todos;

import org.junit.*;
import org.springframework.test.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@ActiveProfiles("test")
@Transactional
public class ContextJpaDaoTest
    extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ContextJpaDao contextJpaDao = null;

    @Test
    public void testCreateAndRemove() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);
	Assert.assertNotNull(context.getId());

	// remove a context
	contextJpaDao.remove(context);
	context = contextJpaDao.get(context.getId());
	Assert.assertNull(context);
    }

    @Test
    public void testGetAll() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);
	Assert.assertNotNull(context.getId());

	// get all contexts
	List contexts = contextJpaDao.getAll();
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
	context = contextJpaDao.persist(context);

	// fetch the context
	Context contextFetched = contextJpaDao.get(context.getId());
	Assert.assertNotNull(contextFetched);

	// remove the context
	contextJpaDao.remove(context);
    }

    @Test
    public void testMerge() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persist(context);

	// update the context
	context.setName("Home");
	context = contextJpaDao.merge(context);
	Assert.assertEquals(context.getName(), "Home");

	// remove the context
	contextJpaDao.remove(context);
    }
}
