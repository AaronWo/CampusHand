package cn.edu.xmu.campushand.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.dao.XMUUserDao;
import cn.edu.xmu.campushand.model.XMUUser;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class XMUUserDaoImplTest {

	private XMUUser xmuUser;

	@Autowired
	@Qualifier("xmuUserDao")
	private XMUUserDao xmuUserDao;

	@Before
	public void setUp() throws Exception {
		xmuUser = new XMUUser();
		xmuUser.setUsername("30920112202501");
		xmuUser.setPassword("Wo123213");
		xmuUser.setWechatId("wo123");
	}

	@Test
	public void testInsert() {
		xmuUserDao.insert(xmuUser);
	}

	@Test
	public void testDelete() {
		xmuUser = xmuUserDao.query("123");
		xmuUserDao.delete(xmuUser);
	}

	@Test
	public void testUpdate() {
		xmuUser = xmuUserDao.query(1);
		xmuUser.setWechatId("123");
		xmuUserDao.update(xmuUser);
	}

	@Test
	public void testQueryInt() {
		xmuUserDao.query(1);
	}

	@Test
	public void testQueryString() {
		xmuUserDao.query("wo123");
	}

}
