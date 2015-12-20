package cn.edu.xmu.campushand.dao;

import cn.edu.xmu.campushand.model.User;

/**
 * 用户Dao的接口
 * 
 * @author Wo
 * 
 */
public interface UserDao {
	public void insert(User user);

	public void delete(User user);

	public void update(User user);

	public User query(String wechatId);
}
