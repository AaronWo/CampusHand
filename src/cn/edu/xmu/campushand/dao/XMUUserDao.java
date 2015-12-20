package cn.edu.xmu.campushand.dao;

import cn.edu.xmu.campushand.model.XMUUser;

/**
 * 厦门大学用户Dao的接口
 * 
 * @author Wo
 * 
 */
public interface XMUUserDao {
	public void insert(XMUUser user);

	public void delete(XMUUser user);

	public void update(XMUUser user);

	public XMUUser query(int id);

	public XMUUser query(String wechatId);
}
