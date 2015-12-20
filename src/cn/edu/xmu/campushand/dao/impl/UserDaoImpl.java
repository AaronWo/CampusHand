package cn.edu.xmu.campushand.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.xmu.campushand.dao.UserDao;
import cn.edu.xmu.campushand.model.User;

/**
 * 用户接口的实现类
 * 
 * @author Wo
 * 
 */
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Override
	public void insert(User user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	@Override
	public void update(User user) {
		getHibernateTemplate().update(user);
	}

	@Override
	public User query(final String wechatId) {
		final String hql = "from User u where u.wechatId = :u_wechatId";
		User user = getHibernateTemplate().execute(
				new HibernateCallback<User>() {

					@Override
					public User doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString("u_wechatId", wechatId);
						User user = null;
						@SuppressWarnings("unchecked")
						List<Object> list = query.list();
						if (list.size() > 0)
							user = (User) list.get(0);
						return user;
					}

				});
		return user;
	}

}
