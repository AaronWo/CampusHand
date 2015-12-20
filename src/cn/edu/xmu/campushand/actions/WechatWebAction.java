package cn.edu.xmu.campushand.actions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.edu.xmu.campushand.displaymodel.ScoreEntry;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.exceptions.NoScoreException;
import cn.edu.xmu.campushand.exceptions.TokenExpairException;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IControlWebService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 用来处理链接的请求，在请求参数中包含用户的OpenId
 * 
 * @author Wo
 * 
 */
public class WechatWebAction extends ActionSupport {

	static Logger logger = Logger.getLogger(WechatWebAction.class);

	private static final long serialVersionUID = 6711622519464486427L;
	private List<ScoreEntry> scoreDisplays = new ArrayList<ScoreEntry>();

	@Autowired
	@Qualifier("controlWebService")
	private IControlWebService controlWebService;

	public IControlWebService getControlWebService() {
		return controlWebService;
	}

	public void setControlWebService(IControlWebService controlWebService) {
		this.controlWebService = controlWebService;
	}

	public List<ScoreEntry> getScoreDisplays() {
		return scoreDisplays;
	}

	public void setScoreDisplays(List<ScoreEntry> scoreDisplays) {
		this.scoreDisplays = scoreDisplays;
	}

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/**
	 * 查询成绩
	 * 
	 * @return scoreDisplays
	 * @throws Exception
	 */
	public String checkScore() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (scoreDisplays != null) {
			scoreDisplays.clear();
		}
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		UserParameter userParameter = new UserParameter();
		String openId = request.getParameter("openid");
		int term = Integer.parseInt(request.getParameter("term"));
		userParameter.setOpenid(openId);
		userParameter.setTerm(term);
		try {
			scoreDisplays = controlWebService.scoreQuery(userParameter);
			request.setAttribute("scoreList", scoreDisplays);
			return "score";
		} catch (TokenExpairException e) {
			request.setAttribute("errorMsg", e.getMessage());
			return "scoreError";
		} catch (ClientProtocolException e) {
			request.setAttribute("errorMsg", "学校的小霸王又坏了呢！");
			return "scoreError";
		} catch (IOException e) {
			request.setAttribute("errorMsg", "学校的小霸王又坏了呢！");
			return "scoreError";
		} catch (LoginFailException e) {
			request.setAttribute("errorMsg", "不能登录了？是不是更改密码之后没有重新绑定呢？");
			return "scoreError";
		} catch (NetworkException e) {
			request.setAttribute("errorMsg", "学校的小霸王又坏了呢！");
			return "scoreError";
		} catch (NoScoreException e) {
			request.setAttribute("errorMsg", e.getMessage());
			return "scoreError";
		} catch (Exception e) {
			request.setAttribute("errorMsg", "出现问题了！");
			return "scoreError";
		}
	}
}
