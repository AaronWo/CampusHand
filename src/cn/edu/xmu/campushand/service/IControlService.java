package cn.edu.xmu.campushand.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;

import cn.edu.xmu.campushand.exceptions.BandFailException;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 
 * @author Wo
 * 
 */
public interface IControlService {

	/**
	 * 处理从Action发来的Request请求
	 * 
	 * @param request
	 *            包含微信封装的消息信息
	 * @return
	 */
	public String processRequest(HttpServletRequest request);

	/**
	 * 处理公众号的纯文本
	 * 
	 * @param requestMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetworkException
	 * @throws LoginFailException
	 * @throws BandFailException
	 */
	public String processTextRequset(Map<String, String> requestMap)
			throws ClientProtocolException, IOException, NetworkException,
			LoginFailException, BandFailException;

	/**
	 * 处理click类型的自定义菜单事件
	 * 
	 * @param requestMap
	 * @return
	 * @throws NetworkException
	 * @throws LoginFailException
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String processClickRequest(Map<String, String> requestMap)
			throws ClientProtocolException, IOException, LoginFailException,
			NetworkException;

	/**
	 * 处理新订阅的事件
	 * 
	 * @param requestMap
	 * @return
	 */
	public String processSubscribe(Map<String, String> requestMap);

	/**
	 * 
	 * 处理用户绑定事件 已处理重复绑定的问题
	 * 
	 * @param userParameter
	 * @return
	 * @throws NetworkException
	 * @throws LoginFailException
	 * @throws BandFailException
	 */
	public String band(UserParameter userParameter) throws NetworkException,
			LoginFailException, BandFailException;
}
