package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xmu.campushand.dao.UserDao;
import cn.edu.xmu.campushand.dao.YJLGUserDao;
import cn.edu.xmu.campushand.displaymodel.ScoreEntry;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.exceptions.NoScoreException;
import cn.edu.xmu.campushand.exceptions.TokenExpairException;
import cn.edu.xmu.campushand.model.User;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IControlWebService;
import cn.edu.xmu.campushand.service.ILogin;
import cn.edu.xmu.campushand.service.IScore;

/**
 * 
 * @author Wo
 * 
 */
public class ControlWebService implements IControlWebService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("yjlgUserDao")
	private YJLGUserDao yjlgUserDao;

	@Autowired
	@Qualifier("yjlgLoginService")
	private ILogin yjlgLoginService;

	@Autowired
	@Qualifier("yjlgScoreService")
	private IScore yjlgScoreService;

	public YJLGUserDao getYjlgUserDao() {
		return yjlgUserDao;
	}

	public void setYjlgUserDao(YJLGUserDao yjlgUserDao) {
		this.yjlgUserDao = yjlgUserDao;
	}

	public ILogin getYjlgLoginService() {
		return yjlgLoginService;
	}

	public void setYjlgLoginService(ILogin yjlgLoginService) {
		this.yjlgLoginService = yjlgLoginService;
	}

	public IScore getYjlgScoreService() {
		return yjlgScoreService;
	}

	public void setYjlgScoreService(IScore yjlgScoreService) {
		this.yjlgScoreService = yjlgScoreService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ScoreEntry> scoreQuery(UserParameter userParameter)
			throws ClientProtocolException, IOException, LoginFailException,
			NetworkException, TokenExpairException, NoScoreException {
		List<ScoreEntry> list = null;
		User user = userDao.query(userParameter.getOpenid());
		if (user == null) {

		} else {
			/**
			 * 如果连接超时，那么提示用户
			 */
			if (!user.checkToken()) {
				throw new TokenExpairException("该链接已失效。请重新点击查询成绩菜单");
			}
			/**
			 * 如果链接没有超时，那么进行查询操作
			 */
			userParameter = user.updateParameter(userParameter);
			if (yjlgLoginService.login(userParameter)) {
				list = yjlgScoreService.getScoreEntries(userParameter);
				if (list.size() < 1)
					throw new NoScoreException("暂无成绩");
			}
		}
		return list;
	}
}
