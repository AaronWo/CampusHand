package cn.edu.xmu.campushand.junit;

import java.io.IOException;

import org.apache.http.impl.client.BasicCookieStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.impl.YJLGLoginService;
import cn.edu.xmu.campushand.service.impl.YJLGScoreService;
import cn.edu.xmu.campushand.util.ConstUtil;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class YJLGLoginServiceTest {

	@Autowired
	@Qualifier("yjlgLoginService")
	private YJLGLoginService service;

	@Autowired
	@Qualifier("yjlgScoreService")
	private YJLGScoreService scoreService;

	private UserParameter parameter;

	@Before
	public void setUp() throws Exception {
		parameter = new UserParameter();
		BasicCookieStore cs = new BasicCookieStore();
		parameter.setCookieStore(cs);
		parameter.setUsername("110230100");
		parameter.setPassword("OTIAJM");
		parameter.setTerm(ConstUtil.YEAR_THREE_2);
		parameter.setSubject("财务管理");
	}

	@Test
	public void testLogin() {
		try {
			if (service.login(parameter)) {
				parameter.setTerm(ConstUtil.YEAR_ONE_1);
				scoreService.getScore(parameter);
				// scoreService.getScoreEntries(parameter);
				// scoreService.calculateGPA(parameter);
				// scoreService.queryAccomplishment(parameter);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		} catch (LoginFailException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBand() {
		try {
			if (service.login(parameter))
				service.band(parameter);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		} catch (LoginFailException e) {
			e.printStackTrace();
		}
	}

}
