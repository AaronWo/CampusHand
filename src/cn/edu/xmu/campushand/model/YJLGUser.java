package cn.edu.xmu.campushand.model;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.ICourse;
import cn.edu.xmu.campushand.service.IInfo;
import cn.edu.xmu.campushand.service.ILogin;
import cn.edu.xmu.campushand.service.IScore;

/**
 * 燕京理工用户类
 * 
 * @author Wo @ 燕京理工
 */
@Entity
@DiscriminatorValue("燕京理工")
public class YJLGUser extends User implements Serializable {

	private static final long serialVersionUID = -4628216258062149006L;

	@Transient
	@Autowired
	@Qualifier("yjlgLoginService")
	private ILogin login;

	@Transient
	private IInfo info;

	@Transient
	@Autowired
	@Qualifier("yjlgScoreService")
	private IScore score;

	@Transient
	private ICourse course;

	public ILogin getLogin() {
		return login;
	}

	public void setLogin(ILogin login) {
		this.login = login;
	}

	public void setInfo(IInfo info) {
		this.info = info;
	}

	public void setScore(IScore score) {
		this.score = score;
	}

	public void setCourse(ICourse course) {
		this.course = course;
	}

	public UserParameter toUserParameter() {
		UserParameter userParameter = new UserParameter();
		userParameter.setUsername(getUsername());
		userParameter.setPassword(getPassword());
		userParameter.setWechatId(getWechatId());
		userParameter.setSex(getUserInfo().getSex());
		userParameter.setName(getUserInfo().getName());
		userParameter.setUniversity(getUserInfo().getUniversity());
		userParameter.setSubject(getUserInfo().getSubject());
		return userParameter;
	}

	public String getScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException {
		try {
			return score.getScore(userParameter);
		} catch (NetworkException e) {
			throw e;
		}
	}

	@Override
	public String getCourse(UserParameter userParameter) {
		return course.getCourseToday(userParameter);
	}

	@Override
	public String getCourseToday(UserParameter userParameter) {
		// TODO Auto-generated method stub
		return course.getCourseToday(userParameter);
	}

	@Override
	public String getCourseTomorrow(UserParameter userParameter) {
		return course.getCourseTomorrow(userParameter);
	}

}
