package cn.edu.xmu.campushand.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.edu.xmu.campushand.dao.UserDao;
import cn.edu.xmu.campushand.model.User;
import cn.edu.xmu.campushand.model.UserInfo;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IInfo;

/**
 * 
 * @author Wo
 * 
 */
public class YJLGInfoService implements IInfo {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * 
	 */
	@Override
	public UserInfo getInfo(UserParameter userParameter) {
		User user = userDao.query(userParameter.getWechatId());
		UserInfo userInfo = user.getUserInfo();
		return userInfo;
	}

}
