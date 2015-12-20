package cn.edu.xmu.campushand.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.ICourse;

/**
 * 
 * @author Wo
 * 
 * 
 * 
 */
public class XMUCourseService implements ICourse {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getCourse(UserParameter userParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getCourseToday(UserParameter userParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getCourseTomorrow(UserParameter userParameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
