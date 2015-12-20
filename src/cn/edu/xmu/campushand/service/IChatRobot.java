package cn.edu.xmu.campushand.service;

import java.util.Map;

/**
 * 处理公众号聊天的请求
 * 
 * @author Wo
 * 
 */
public interface IChatRobot {
	/**
	 * 回复用户的聊天
	 * 
	 * @param chat
	 *            用户输入聊天内容
	 * @return 机器人回复的内容
	 */
	public String responseChat(Map<String, String> requestMap, String chat);
}
