package cn.edu.xmu.campushand.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.xmu.campushand.dao.UserInfoDao;
import cn.edu.xmu.campushand.model.UserInfo;

/**
 * 用户额外信息的实现类
 * 
 * @author Wo
 * 
 *         已测试
 * 
 */
public class UserInfoDaoImpl extends HibernateDaoSupport implements UserInfoDao {

	@Override
	public void insert(UserInfo userInfo) {
		getHibernateTemplate().save(userInfo);
	}

	@Override
	public void delete(UserInfo userInfo) {
		getHibernateTemplate().delete(userInfo);
	}

	@Override
	public void update(UserInfo userInfo) {
		getHibernateTemplate().update(userInfo);
	}

	@Override
	public UserInfo query(final int id) {
		UserInfo userInfo = null;
		final String hql = "from UserInfo u where u.id = :u_id";
		userInfo = getHibernateTemplate().execute(
				new HibernateCallback<UserInfo>() {

					@Override
					public UserInfo doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setInteger("u_id", id);
						UserInfo userInfo = (UserInfo) query.list().get(0);
						return userInfo;
					}
				});
		return userInfo;
	}

}
