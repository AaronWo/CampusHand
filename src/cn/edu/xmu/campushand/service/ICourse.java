package cn.edu.xmu.campushand.service;

import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 
 * @author Wo
 * 
 */
public interface ICourse {

	public String getCourse(UserParameter userParameter);

	public String getCourseToday(UserParameter userParameter);

	public String getCourseTomorrow(UserParameter userParameter);

}
