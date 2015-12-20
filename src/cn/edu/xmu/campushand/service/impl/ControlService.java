package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xmu.campushand.dao.UserDao;
import cn.edu.xmu.campushand.dao.YJLGUserDao;
import cn.edu.xmu.campushand.exceptions.BandFailException;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.message.resp.NewsMessage;
import cn.edu.xmu.campushand.message.resp.TextMessage;
import cn.edu.xmu.campushand.model.User;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IChatRobot;
import cn.edu.xmu.campushand.service.IControlService;
import cn.edu.xmu.campushand.service.ILogin;
import cn.edu.xmu.campushand.service.IScore;
import cn.edu.xmu.campushand.service.IWebService;
import cn.edu.xmu.campushand.util.ConstUtil;
import cn.edu.xmu.campushand.util.MessageUtil;

/**
 * 处理从Action层传来的请求
 * 
 * @author Wo
 * 
 */
public class ControlService implements IControlService {

	// static Logger logger = Logger.getLogger(ControlService.class);
	static Logger logger = Logger.getLogger(ControlService.class.getName());

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("yjlgUserDao")
	private YJLGUserDao yjlgUserDao;

	@Autowired
	@Qualifier("xmuLoginService")
	private ILogin xmuLoginService;

	@Autowired
	@Qualifier("yjlgLoginService")
	private ILogin yjlgLoginService;

	@Autowired
	@Qualifier("yjlgScoreService")
	private IScore yjlgScoreService;

	@Autowired
	@Qualifier("yjlgWebService")
	private IWebService yjlgWebService;

	@Autowired
	@Qualifier("chatRobotService")
	private IChatRobot chatRobotService;

	public IChatRobot getChatRobotService() {
		return chatRobotService;
	}

	public void setChatRobotService(IChatRobot chatRobotService) {
		this.chatRobotService = chatRobotService;
	}

	public IWebService getYjlgWebService() {
		return yjlgWebService;
	}

	public void setYjlgWebService(IWebService yjlgWebService) {
		this.yjlgWebService = yjlgWebService;
	}

	public IScore getYjlgScoreService() {
		return yjlgScoreService;
	}

	public void setYjlgScoreService(IScore yjlgScoreService) {
		this.yjlgScoreService = yjlgScoreService;
	}

	public YJLGUserDao getYjlgUserDao() {
		return yjlgUserDao;
	}

	public void setYjlgUserDao(YJLGUserDao yjlgUserDao) {
		this.yjlgUserDao = yjlgUserDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ILogin getXmuLoginService() {
		return xmuLoginService;
	}

	public void setXmuLoginService(ILogin xmuLoginService) {
		this.xmuLoginService = xmuLoginService;
	}

	public ILogin getYjlgLoginService() {
		return yjlgLoginService;
	}

	public void setYjlgLoginService(ILogin yjlgLoginService) {
		this.yjlgLoginService = yjlgLoginService;
	}

	/**
	 * 接收从Action层传来的请求，进一步分发
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String processRequest(HttpServletRequest request) {
		String returnMessage = null;

		logger.info("新的请求!");

		Map<String, String> requestMap = null;
		try {
			requestMap = MessageUtil.parseXml(request);
			logger.info(requestMap);
			String msgType = requestMap.get("MsgType");

			/**
			 * 处理文本消息
			 */
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				returnMessage = processTextRequset(requestMap);
			}
			/**
			 * 处理事件消息
			 */
			else if (msgType
					.equalsIgnoreCase(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");
				/**
				 * 处理订阅事件
				 */
				if (eventType
						.equalsIgnoreCase(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					returnMessage = processSubscribe(requestMap);
				}
				/**
				 * 处理click事件
				 */
				else if (eventType
						.equalsIgnoreCase(MessageUtil.EVENT_TYPE_CLICK)) {
					returnMessage = processClickRequest(requestMap);
				}
			}
		} catch (ClientProtocolException e) {
			TextMessage textMessage = createTextMessage(requestMap);
			textMessage.setContent("由于网络问题，教务功能暂时无法使用...");
			logger.error("由于网络问题，教务功能暂时无法使用...");
			return MessageUtil.textMessageToXml(textMessage);
		} catch (IOException e) {
			TextMessage textMessage = createTextMessage(requestMap);
			textMessage.setContent("由于网络问题，教务功能暂时无法使用...");
			logger.error("由于网络问题，教务功能暂时无法使用...");
			return MessageUtil.textMessageToXml(textMessage);
		} catch (NetworkException e) {
			TextMessage textMessage = createTextMessage(requestMap);
			textMessage.setContent("由于网络问题，教务功能暂时无法使用...");
			logger.error("由于网络问题，教务功能暂时无法使用...");
			return MessageUtil.textMessageToXml(textMessage);
		} catch (LoginFailException e) {
			TextMessage textMessage = createTextMessage(requestMap);
			textMessage.setContent("貌似账号密码无法登陆，请重试。。。");
			logger.error("貌似账号密码无法登陆，请重试。。。");
			return MessageUtil.textMessageToXml(textMessage);
		} catch (BandFailException e) {
			TextMessage textMessage = createTextMessage(requestMap);
			textMessage.setContent("貌似不知什么原因倒是绑定失败。。。");
			logger.error("貌似不知什么原因倒是绑定失败。。。");
			return MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			TextMessage textMessage = createTextMessage(requestMap);
			textMessage.setContent("由于网络问题，教务功能暂时无法使用...");
			logger.error("由于网络问题，教务功能暂时无法使用...");
			return MessageUtil.textMessageToXml(textMessage);
		}
		return returnMessage;
	}

	/**
	 * 处理微信文本请求
	 * 
	 * @param requestMap
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws BandFailException
	 * @throws LoginFailException
	 * @throws NetworkException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String processTextRequset(Map<String, String> requestMap)
			throws ClientProtocolException, IOException, NetworkException,
			LoginFailException, BandFailException {
		TextMessage textMessage = createTextMessage(requestMap);
		UserParameter parameter = new UserParameter();
		parameter.setWechatId(requestMap.get("FromUserName")); // 设置wechatId
		String content = requestMap.get("Content");
		if (content.equals("band")) {

		} else if (content.startsWith("绑定")) {
			// content结构： 绑定 账号 密码
			try {
				String[] info = processBandString(content);
				parameter.setUsername(info[0]);
				parameter.setPassword(info[1]);
				parameter.setUniversity("燕京理工");
				textMessage.setContent(band(parameter));
			} catch (ArrayIndexOutOfBoundsException e) {
				logger.error(e);
				textMessage.setContent("您发送的消息格式不正确，请重新确认。。。");
				return MessageUtil.textMessageToXml(textMessage);
			}
		} else if (content.startsWith("成绩")) {
			String[] info = content.split(" ");
			if (info.length == 2) {
				User user = userDao.query(parameter.getWechatId());
				if (user != null) {
					parameter = user.toParameter();
					if (info[1].equals("大一上")) {
						parameter.setTerm(ConstUtil.YEAR_ONE_1);
					} else if (info[1].equals("大一下")) {
						parameter.setTerm(ConstUtil.YEAR_ONE_2);
					} else if (info[1].equals("大二上")) {
						parameter.setTerm(ConstUtil.YEAR_TWO_1);
					} else if (info[1].equals("大二下")) {
						parameter.setTerm(ConstUtil.YEAR_TWO_2);
					} else if (info[1].equals("大三上")) {
						parameter.setTerm(ConstUtil.YEAR_THREE_1);
					} else if (info[1].equals("大三下")) {
						parameter.setTerm(ConstUtil.YEAR_THREE_2);
					} else if (info[1].equals("大四上")) {
						parameter.setTerm(ConstUtil.YEAR_FOUR_1);
					} else if (info[1].equals("大四下")) {
						parameter.setTerm(ConstUtil.YEAR_FOUR_2);
					} else {
						textMessage.setContent("您输入的学期好像不正确，请您重新确认！");
						return MessageUtil.textMessageToXml(textMessage);
					}
					textMessage.setContent(checkScore(parameter));
				} else {
					textMessage.setContent("您还没有绑定账户！请您先绑定账户再完成查询操作");
				}
			} else {
				textMessage.setContent("您发送的消息格式不正确，只需要一个空格，请重新确认。。。");
			}

		} else {
			// 其他情况都当作跟机器人聊天
			return chatRobotService.responseChat(requestMap, content);
		}
		return MessageUtil.textMessageToXml(textMessage);
	}

	/**
	 * 处理绑定请求
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String band(UserParameter userParameter) {
		String returnMessage = "绑定成功！感谢您的合作！请注意删除包含您密码的消息！";
		try {
			User user = userDao.query(userParameter.getWechatId());
			if (user != null)
				returnMessage = "您已绑定成功！无需再次绑定！";
			else {
				if ("厦门大学".equals(userParameter.getUniversity())) {
					xmuLoginService.band(userParameter);
				} else if ("燕京理工".equals(userParameter.getUniversity())) {
					yjlgLoginService.band(userParameter);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
			returnMessage = "绑定失败！";
		} catch (IOException e) {
			logger.error(e);
			returnMessage = "绑定失败！\n";
		} catch (NetworkException e) {
			logger.error(e);
			returnMessage = "貌似因为网络问题，绑定失败。。。";
		} catch (LoginFailException e) {
			logger.error(e);
			returnMessage = "貌似账号密码无法登陆，请重试。。。";
		} catch (BandFailException e) {
			logger.error(e);
			returnMessage = "貌似不知什么原因倒是绑定失败。。。";
		}
		return returnMessage;
	}

	/**
	 * 处理公众号菜单请求
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String processClickRequest(Map<String, String> requestMap)
			throws ClientProtocolException, IOException, LoginFailException,
			NetworkException {
		TextMessage textMessage = createTextMessage(requestMap);
		String eventKey = requestMap.get("EventKey");

		String responseMessage = "";

		if (eventKey.equals("11")) {
			// 绑定账户
			textMessage
					.setContent("欢迎您绑定Campus助手!请回复一下内容绑定账号:\n绑定 空格 用户名 空格 密码\n"
							+ "例如：\n绑定 1232512 123345\n账号密码前一定要加'绑定'这两个字呦~");
			responseMessage = MessageUtil.textMessageToXml(textMessage);
		} else if (eventKey.equals("12")) {
			// 取消绑定
			User user = userDao.query(requestMap.get("FromUserName"));
			if (user == null) {
				textMessage.setContent("您还没有绑定哦 ，所以没办法取消绑定~");
			} else {
				userDao.delete(user);
				textMessage.setContent("取消绑定成功！很遗憾没法再帮到您，欢迎您再次绑定！");
			}
			responseMessage = MessageUtil.textMessageToXml(textMessage);
		}
		/**
		 * 查询成绩说明
		 */
		else if (eventKey.equals("21")) {
			// System.out.println("查询成绩说明");
			User user = userDao.query(requestMap.get("FromUserName"));
			/**
			 * 如果未绑定
			 */
			if (user == null) {
				textMessage.setContent("您还没有绑定，如果想使用我们提供的功能请您先绑定哟~");
			}
			/**
			 * 如果是已经绑定的用户
			 */
			else {
				/**
				 * 更新scoreToken的信息
				 */
				user.updateToken();
				StringBuffer sb = new StringBuffer();
				sb.append("成绩查询：\n");
				String[] strs = new String[] { "大一上学期成绩", "大一下学期成绩", "大二上学期成绩",
						"大二下学期成绩", "大三上学期成绩", "大三下学期成绩", "大四上学期成绩", "大四下学期成绩" };
				for (int i = 1; i <= 8; i++) {
					sb.append("-------------------\n");
					sb.append("<a href=\"http://115.29.28.147/CampusHand/wechatWebAction!checkScore?openid="
							+ user.getWechatId()
							+ "&term="
							+ (i - 1)
							+ "\">"
							+ strs[i - 1] + "</a>\n");
				}
				sb.append("-------------------\n");
				textMessage.setContent(sb.toString());
			}
			responseMessage = MessageUtil.textMessageToXml(textMessage);

		}
		/**
		 * 培养方案查询
		 */
		else if (eventKey.equals("22")) {
			User user = userDao.query(requestMap.get("FromUserName"));
			/**
			 * 如果未绑定
			 */
			if (user == null) {
				textMessage.setContent("您还没有绑定，如果想使用我们提供的功能请您先绑定哟~");
			}
			/**
			 * 如果是已经绑定的用户
			 */
			else {
				UserParameter parameter = user.toParameter();
				if (yjlgLoginService.login(parameter)) {
					textMessage.setContent(yjlgScoreService
							.queryAccomplishment(parameter));
				}
			}
			responseMessage = MessageUtil.textMessageToXml(textMessage);
		}
		/**
		 * 不及格成绩
		 */
		else if (eventKey.equals("23")) {
			textMessage.setContent("本功能正在开发中，马上上线~");
			responseMessage = MessageUtil.textMessageToXml(textMessage);
		}
		/**
		 * 教学评估
		 */
		else if (eventKey.equals("24")) {
			textMessage.setContent("本功能正在开发中，马上上线~");
			responseMessage = MessageUtil.textMessageToXml(textMessage);

		}
		/**
		 * 计算GPA
		 */
		else if (eventKey.equals("25")) {
			User user = userDao.query(requestMap.get("FromUserName"));
			if (user == null) {
				textMessage.setContent("您还没有绑定，如果想使用我们提供的功能请您先绑定哟~");
			} else {
				UserParameter parameter = user.toParameter();
				parameter.setSubject(user.getUserInfo().getSubject());
				if (yjlgLoginService.login(parameter)) {
					textMessage.setContent(yjlgScoreService
							.calculateGPA(parameter));
				}
			}
			responseMessage = MessageUtil.textMessageToXml(textMessage);
		} else if (eventKey.equals("32")) {
			// 查询天气
			textMessage.setContent(yjlgWebService.getWeather("北京"));
			responseMessage = MessageUtil.textMessageToXml(textMessage);
		}
		// TODO 处理其他click事件
		return responseMessage;
	}

	/**
	 * 处理公众号订阅
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public String processSubscribe(Map<String, String> requestMap) {
		TextMessage textMessage = createTextMessage(requestMap);
		textMessage
				.setContent("欢迎您关注Campus助手！我们致力于服务燕京理工的筒子们！小的给您请安啦~您无聊的时候可以跟我聊聊天哦~");
		return MessageUtil.textMessageToXml(textMessage);
	}

	/**
	 * 查询分数
	 * 
	 * @param userParameter
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws LoginFailException
	 * @throws NetworkException
	 */
	private String checkScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, LoginFailException,
			NetworkException {
		String result = "";
		if (yjlgLoginService.login(userParameter)) {
			result = yjlgScoreService.getScore(userParameter);
		}
		return result;
	}

	/**
	 * 处理用户的文本消息
	 * 
	 * @param requestMap
	 * @return
	 */
	static TextMessage createTextMessage(Map<String, String> requestMap) {
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(requestMap.get("ToUserName"));
		textMessage.setToUserName(requestMap.get("FromUserName"));
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		return textMessage;
	}

	static NewsMessage createNewsMessage(Map<String, String> requestMap) {
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(requestMap.get("ToUserName"));
		newsMessage.setToUserName(requestMap.get("FromUserName"));
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		// newsMessage.setFuncFlag(0);
		return newsMessage;
	}

	private String[] processBandString(String str)
			throws ArrayIndexOutOfBoundsException {
		String[] strings = str.split(" ");
		return new String[] { strings[1], strings[2] };
	}

	/**
	 * 根据用户的专业，返回本专业计算GPA的网址
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private String getGpaUrl(User user) {
		if (user.getUserInfo().getSubject().contains("电子信息工程")) {
			return "http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=fainfo&fajhh=469";
		} else if (user.getUserInfo().getSubject().contains("财务管理")) {
			return "http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=fainfo&fajhh=479#qb_479";
		} else if (user.getUserInfo().getSubject().contains("艺术设计")) {
			return "http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=fainfo&fajhh=458#qb_458";
		} else if (user.getUserInfo().getSubject().contains("通信工程")) {
			return "http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=fainfo&fajhh=479#qb_479";
		} else {
			return null;
		}
	}
}
