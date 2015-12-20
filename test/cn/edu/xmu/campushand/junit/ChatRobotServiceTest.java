package cn.edu.xmu.campushand.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.xmu.campushand.service.IChatRobot;

/**
 * 
 * @author Wo
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:WebContent/WEB-INF/applicationContext.xml" })
public class ChatRobotServiceTest {

	@Autowired
	@Qualifier("chatRobotService")
	private IChatRobot chatRobot;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testResponseChat() {
		System.out.println(chatRobot.responseChat(null, "北京到厦门机票 明天"));
	}

}
