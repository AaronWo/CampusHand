package cn.edu.xmu.campushand.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.dao.YJLGUserDao;
import cn.edu.xmu.campushand.model.YJLGUser;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class YJLGUserDaoImplTest {

	@Autowired
	@Qualifier("yjlgUserDao")
	private YJLGUserDao dao;
	private YJLGUser user;

	@Before
	public void setUp() throws Exception {
		user = new YJLGUser();
		user.setUsername("110230100");
		user.setPassword("OTIAJM");
		user.setWechatId("wohaosu123");
	}

	@Test
	public void testInsert() {
		dao.insert(user);
	}

	@Test
	public void testDelete() {
		user = dao.query("wyr123");
		dao.delete(user);
	}

	@Test
	public void testUpdate() {
		user = dao.query(user.getWechatId());
		user.setWechatId("wyr123");
		dao.update(user);
		System.out.println(user.getWechatId());

	}

	@Test
	public void testQueryInt() {
		user = dao.query(1);
		System.out.println(user.getWechatId());
	}

	@Test
	public void testQueryString() {
		dao.query("wohaosu123");
		System.out.println(user.getWechatId());

	}

}
