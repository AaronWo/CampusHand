package cn.edu.xmu.campushand.dao;

import cn.edu.xmu.campushand.model.UserInfo;

/**
 * 用户额外信息Dao的接口
 * 
 * @author Wo
 * 
 */
public interface UserInfoDao {
	public void insert(UserInfo userInfo);

	public void delete(UserInfo userInfo);

	public void update(UserInfo userInfo);

	public UserInfo query(int id);
}
