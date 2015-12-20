package cn.edu.xmu.campushand.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.http.client.ClientProtocolException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.ICourse;
import cn.edu.xmu.campushand.service.ILogin;
import cn.edu.xmu.campushand.service.IScore;

/**
 * 厦门大学用户类
 * 
 * @author Wo 厦门大学
 */

@Entity
@DiscriminatorValue("厦门大学")
public class XMUUser extends User implements Serializable {

	private static final long serialVersionUID = 2589966084732172109L;

	@Transient
	@Autowired
	@Qualifier("xmuLoginService")
	private ILogin login;

	@Transient
	@Autowired
	@Qualifier("xmuCourseService")
	private ICourse course;

	@Transient
	@Autowired
	@Qualifier("xmuScoreService")
	private IScore score;

	@OneToMany
	@Cascade(value = CascadeType.ALL)
	private List<Lecture> lectures;

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public ILogin getLogin() {
		return login;
	}

	public void setLogin(ILogin login) {
		this.login = login;
	}

	public void setCourse(ICourse course) {
		this.course = course;
	}

	public void setScore(IScore score) {
		this.score = score;
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

	@Override
	public String getScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException {
		try {
			return score.getScore(userParameter);
		} catch (NetworkException e) {
			throw e;
		}
	}

	/**
	 * 查课表
	 */
	@Override
	public String getCourse(UserParameter userParameter) {
		return course.getCourse(userParameter);
	}

	@Override
	public String getCourseToday(UserParameter userParameter) {
		return course.getCourseToday(userParameter);
	}

	@Override
	public String getCourseTomorrow(UserParameter userParameter) {
		return course.getCourseTomorrow(userParameter);
	}

}
