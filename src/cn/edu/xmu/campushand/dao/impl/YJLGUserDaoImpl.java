package cn.edu.xmu.campushand.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.xmu.campushand.dao.YJLGUserDao;
import cn.edu.xmu.campushand.model.YJLGUser;

/**
 * 燕京理工学生用户的实现类
 * 
 * @author Wo
 * 
 *         已测试
 */
public class YJLGUserDaoImpl extends HibernateDaoSupport implements YJLGUserDao {

	@Override
	public void insert(YJLGUser user) {
		getHibernateTemplate().save(user);
	}

	@Override
	public void delete(YJLGUser user) {
		getHibernateTemplate().delete(user);
	}

	@Override
	public void update(YJLGUser user) {
		getHibernateTemplate().update(user);
	}

	@Override
	public YJLGUser query(final int id) {
		YJLGUser user = null;
		final String hql = "from YJLGUser user where user.id = :u_id";
		user = getHibernateTemplate().execute(
				new HibernateCallback<YJLGUser>() {

					@Override
					public YJLGUser doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setInteger("u_id", id);
						YJLGUser user = (YJLGUser) query.list().get(0);
						return user;
					}
				});
		return user;
	}

	/**
	 * 当查询不到的时候会出错！ TODO
	 */
	@Override
	public YJLGUser query(final String wechatid) {
		YJLGUser user = null;
		final String hql = "from YJLGUser user where user.wechatId = :u_wechatId";
		user = getHibernateTemplate().execute(
				new HibernateCallback<YJLGUser>() {

					@Override
					public YJLGUser doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString("u_wechatId", wechatid);
						YJLGUser user = null;
						@SuppressWarnings("unchecked")
						List<Object> list = query.list();
						if (list.size() > 0)
							user = (YJLGUser) list.get(0);
						return user;
					}
				});
		return user;
	}

}
