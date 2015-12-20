package cn.edu.xmu.campushand.dao.impl;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.xmu.campushand.dao.XMUUserDao;
import cn.edu.xmu.campushand.model.XMUUser;

/**
 * 厦大学生Dao的实现类
 * 
 * @author Wo
 * 
 *         已测试
 * 
 */
public class XMUUserDaoImpl extends HibernateDaoSupport implements XMUUserDao {

	@Override
	public void insert(XMUUser user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public void delete(XMUUser user) {
		getHibernateTemplate().delete(user);
	}

	@Override
	public void update(XMUUser user) {
		getHibernateTemplate().update(user);
	}

	@Override
	public XMUUser query(final int id) {
		XMUUser user = null;
		final String hql = "from XMUUser user where user.id = :u_id";
		user = getHibernateTemplate().execute(new HibernateCallback<XMUUser>() {

			@Override
			public XMUUser doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setInteger("u_id", id);
				XMUUser user = (XMUUser) query.list().get(0);
				return user;
			}
		});
		return user;
	}

	@Override
	public XMUUser query(final String wechatId) {
		XMUUser user = null;
		final String hql = "from XMUUser user where user.wechatId = :u_wechatId";
		user = getHibernateTemplate().execute(new HibernateCallback<XMUUser>() {

			@Override
			public XMUUser doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setString("u_wechatId", wechatId);
				XMUUser user = (XMUUser) query.list().get(0);
				return user;
			}
		});
		return user;
	}

}
