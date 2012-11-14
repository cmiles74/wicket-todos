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
    public void testCreateContext() {

	Context context = new Context();
	context.setName("Office");
	context.setDescription("Wherever my laptop is.");
	context = contextJpaDao.persistContext(context);

	Assert.assertNotNull(context.getId());
    }
}
