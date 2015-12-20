package cn.edu.xmu.campushand.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.edu.xmu.campushand.exceptions.BandFailException;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IControlService;
import cn.edu.xmu.campushand.util.SignUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 接受微信后台发来的Http请求
 * 
 * @author Wo
 * 
 */
public class WechatAction extends ActionSupport {

	private static final long serialVersionUID = 3727996838374563760L;

	static Logger logger = Logger.getLogger(WechatAction.class);

	@Autowired
	@Qualifier("controlService")
	private IControlService controlService;

	public IControlService getControlService() {
		return controlService;
	}

	public void setControlService(IControlService controlService) {
		this.controlService = controlService;
	}

	/**
	 * 分析请求类型，分派给不同方法处理
	 */
	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		logger.info("Action excute");

		String method = request.getMethod();
		boolean isPostMethod = "POST".equalsIgnoreCase(method);
		if (isPostMethod)
			return processPost(request, response);
		else {
			return processGet(request, response);
		}
	}

	/**
	 * 处理绑定请求
	 * 
	 * @return
	 */
	public String band() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		UserParameter userParameter = createUserParameter(request);
		String result;
		try {
			result = controlService.band(userParameter);
			request.setAttribute("myResult", "绑定成功");
			request.setAttribute("myReason", result);
		} catch (NetworkException e) {
			request.setAttribute("myResult", "绑定失败");
			request.setAttribute("myReason", "服务器网络异常");
		} catch (LoginFailException e) {
			request.setAttribute("myResult", "绑定失败");
			request.setAttribute("myReason", "无法登陆");
		} catch (BandFailException e) {
			request.setAttribute("myResult", "绑定失败");
			request.setAttribute("myReason", "账号或密码错误");
		}
		return "band";
	}

	private UserParameter createUserParameter(HttpServletRequest request) {
		// TODO 加入wechatid
		UserParameter userParameter = new UserParameter();
		userParameter.setUniversity(request.getParameter("univer"));
		userParameter.setUsername(request.getParameter("username"));
		userParameter.setPassword(request.getParameter("password"));
		return userParameter;
	}

	/**
	 * 处理用户发来的消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private String processPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		// 调用核心业务类接收消息、处理消息
		String respMessage = controlService.processRequest(request);

		// 响应消息
		out.print(respMessage);
		out.close();

		return null;
	}

	/**
	 * 处理微信验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private String processGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();

		return null;
	}

}
