package cn.edu.xmu.campushand.dao;

import cn.edu.xmu.campushand.model.YJLGUser;

/**
 * 燕京理工用户Dao的接口
 * 
 * @author Wo
 * 
 */
public interface YJLGUserDao {
	public void insert(YJLGUser user);

	public void delete(YJLGUser user);

	public void update(YJLGUser user);

	public YJLGUser query(int id);

	public YJLGUser query(String wechatid);
}
