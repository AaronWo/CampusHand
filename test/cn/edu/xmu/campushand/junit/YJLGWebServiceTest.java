package cn.edu.xmu.campushand.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.service.IWebService;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class YJLGWebServiceTest {

	@Autowired
	@Qualifier("yjlgWebService")
	private IWebService yjlgWebService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetWeather() {
		System.out.println(yjlgWebService.getWeather("北京"));
	}

}
