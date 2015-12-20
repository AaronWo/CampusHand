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

import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IInfo;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class XMUInfoServiceTest {

	@Autowired
	@Qualifier("xmuInfoService")
	private IInfo infoService;

	private UserParameter userParameter;

	@Before
	public void setUp() throws Exception {
		userParameter = new UserParameter();
		userParameter.setPassword("Wo123213");
		userParameter.setUsername("30920112202501");
	}

	@Test
	public void testGetInfo() {
		try {
			infoService.getInfo(userParameter);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
