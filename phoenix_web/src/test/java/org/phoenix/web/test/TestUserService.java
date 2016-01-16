package org.phoenix.web.test;

import java.util.Date;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IUserService;
import org.phoenix.web.util.ShiroKit;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserService {
	
/*	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private IUserService userService;

	@Before
	public void setUp() {
		//此时最好不要使用Spring的Transactional来管理，因为dbunit是通过jdbc来处理connection，再使用spring在一些编辑操作中会造成事务shisu
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
		//SystemContext.setRealPath("D:\\teach_source\\class2\\j2EE\\dingan\\cms-da\\src\\main\\webapp");
	}
	
	@After
	public void tearDown() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession(); 
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	}
	
	@Test
	public void testAdd() {
		User u = new User();
		u.setUsername("admin");
		u.setNickname("管理员");
		u.setPassword("admin");
		u.setRole(0);
		u.setRoleName("管理员");
		u.setEmail("5156meng.feiyang@163.com");
		u.setCreateDate(new Date());
		userService.add(u);
	}*/
	
	@Test
	public void testMd5(){
		System.out.println(ShiroKit.md5("admin1", "admin1"));
	}
	@Test
	public void testBase64(){
		System.out.println(ShiroKit.base64Encode("admin1"));
		System.out.println(ShiroKit.base64Decode(ShiroKit.base64Encode("admin1")));
	}
}
