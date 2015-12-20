package cn.edu.xmu.campushand.dao;

import cn.edu.xmu.campushand.model.YJLGMajor;

/**
 * 燕京理工课程Dao的接口
 * 
 * @author Wo
 * 
 */
public interface YJLGMajorDao {
	/**
	 * 将新数据插入数据库
	 * 
	 * @param yjlgMajor
	 */
	public void insert(YJLGMajor yjlgMajor);

	/**
	 * 删除数据库中的一条记录
	 * 
	 * @param yjlgMajor
	 */
	public void delete(YJLGMajor yjlgMajor);

	/**
	 * 更新一条数据库中记录
	 * 
	 * @param yjlgMajor
	 */
	public void update(YJLGMajor yjlgMajor);

	/**
	 * 根据专业名称查询
	 * 
	 * @param major
	 * @return 如果存在，则返回YJLGMajor对象，否则返回null
	 */
	public YJLGMajor queryByMajor(String major);
}
