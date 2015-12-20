package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xmu.campushand.dao.UserDao;
import cn.edu.xmu.campushand.model.User;
import cn.edu.xmu.campushand.model.UserInfo;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IInfo;

/**
 * 
 * @author Wo 已测试
 * 
 */
public class XMUInfoService implements IInfo {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public UserInfo getInfo(UserParameter userParameter) throws ParseException,
			IOException {
		User user = userDao.query(userParameter.getWechatId());
		UserInfo userInfo = user.getUserInfo();
		return userInfo;
	}
}
