package cn.edu.xmu.campushand.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.dao.UserInfoDao;
import cn.edu.xmu.campushand.model.UserInfo;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class UserInfoDaoTest {

	@Autowired
	@Qualifier("userInfoDao")
	private UserInfoDao dao;

	private UserInfo userInfo;

	@Before
	public void setUp() throws Exception {
		userInfo = new UserInfo();
		userInfo.setName("wangyiran");
		userInfo.setSex("男");
		userInfo.setSubject("xxx");
		userInfo.setUniversity("燕京理工");
	}

	@Test
	public void testInsert() {
		dao.insert(userInfo);
	}

	@Test
	public void testDelete() {
		userInfo = dao.query(1);
		dao.delete(userInfo);
	}

	@Test
	public void testUpdate() {
		userInfo = dao.query(1);
		userInfo.setName("wohaosu");
		dao.update(userInfo);
	}

	@Test
	public void testQuery() {
		userInfo = dao.query(1);
	}

}
