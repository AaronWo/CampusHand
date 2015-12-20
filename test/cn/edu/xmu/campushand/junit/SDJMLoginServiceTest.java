package cn.edu.xmu.campushand.junit;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
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
import cn.edu.xmu.campushand.service.ILogin;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class SDJMLoginServiceTest {

	@Autowired
	@Qualifier("sdjmLoginService")
	private ILogin service;

	private UserParameter parameter;

	public ILogin getService() {
		return service;
	}

	public void setService(ILogin service) {
		this.service = service;
	}

	public UserParameter getParameter() {
		return parameter;
	}

	public void setParameter(UserParameter parameter) {
		this.parameter = parameter;
	}

	@Before
	public void setUp() throws Exception {
		parameter = new UserParameter();
		parameter.setPassword("22013100615");
		parameter.setUsername("22013100615");
	}

	@Test
	public void testLogin() throws NetworkException {
		try {
			service.login(parameter);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LoginFailException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testBand() {
	}

}
