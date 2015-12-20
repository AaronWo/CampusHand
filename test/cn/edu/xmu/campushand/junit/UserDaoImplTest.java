package cn.edu.xmu.campushand.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.dao.UserDao;
import cn.edu.xmu.campushand.model.User;
import cn.edu.xmu.campushand.model.XMUUser;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class UserDaoImplTest {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testQuery() {
		User user = userDao.query("123456");
		System.out.println(user);
		System.out.println(user.getClass());
		XMUUser xmuUser = (XMUUser) user;
		System.out.println(xmuUser.getUsername());
	}

}
