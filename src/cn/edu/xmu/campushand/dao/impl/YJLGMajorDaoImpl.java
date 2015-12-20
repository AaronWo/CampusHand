package cn.edu.xmu.campushand.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.edu.xmu.campushand.dao.YJLGMajorDao;
import cn.edu.xmu.campushand.model.YJLGMajor;

/**
 * 燕京理工用户课程的实现类
 * 
 * @author guohaosu
 *
 */
public class YJLGMajorDaoImpl extends HibernateDaoSupport implements
		YJLGMajorDao {

	@Override
	public void insert(YJLGMajor yjlgMajor) {
		getHibernateTemplate().save(yjlgMajor);
	}

	@Override
	public void delete(YJLGMajor yjlgMajor) {
		getHibernateTemplate().delete(yjlgMajor);
	}

	@Override
	public void update(YJLGMajor yjlgMajor) {
		getHibernateTemplate().update(yjlgMajor);
	}

	@Override
	public YJLGMajor queryByMajor(final String major) {
		final String hql = "from YJLGMajor ym where ym.majorName = :name";
		YJLGMajor yjlgMajor = null;
		yjlgMajor = getHibernateTemplate().execute(
				new HibernateCallback<YJLGMajor>() {
					@Override
					public YJLGMajor doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						query.setString("name", major);
						@SuppressWarnings("unchecked")
						List<YJLGMajor> list = query.list();
						YJLGMajor yjlgMajor = null;
						if (list != null && list.size() > 0)
							yjlgMajor = list.get(0);
						return yjlgMajor;
					}
				});
		return yjlgMajor;
	}
}
