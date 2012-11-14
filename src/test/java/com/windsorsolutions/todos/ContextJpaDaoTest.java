package com.windsorsolutions.todos;

import org.junit.*;
import org.springframework.test.*;
import org.springframework.test.context.*;
import org.springframework.transaction.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@Transactional
public class ContextJpaDaoTest
    extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    ContextJpaDao contextJpaDao = null;

    @Test
    public void testCreateAndRemoveContext() {

	// create a context
	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persistContext(context);
	Assert.assertNotNull(context.getId());

	// remove a context
	contextJpaDao.removeContext(context);
	context = contextJpaDao.getContext(context.getId());
	Assert.assertNull(context);
    }

    @Test
    public void testGetContext() {

	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persistContext(context);

	Context contextFetched = contextJpaDao.getContext(context.getId());
	Assert.assertNotNull(contextFetched);

	contextJpaDao.removeContext(context);
    }
}
