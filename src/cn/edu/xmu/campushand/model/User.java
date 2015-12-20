package cn.edu.xmu.campushand.model;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.BasicCookieStore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 用户类的基类
 * 
 * @author Wo
 * 
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "UNIVERSITY_NAME")
@DiscriminatorValue("UNIVERSITY")
public abstract class User implements Serializable {

	private static final long serialVersionUID = 5521743935809618230L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	private String username;

	private String password;

	@OneToOne
	@Cascade(value = CascadeType.ALL)
	private UserInfo userInfo;

	@OneToOne
	@Cascade(value = CascadeType.ALL)
	private UserToken userToken;

	private String wechatId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserToken getUserToken() {
		return userToken;
	}

	public void setUserToken(UserToken userToken) {
		this.userToken = userToken;
	}

	abstract public String getScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException;

	abstract public String getCourse(UserParameter userParameter);

	abstract public String getCourseToday(UserParameter userParameter);

	abstract public String getCourseTomorrow(UserParameter userParameter);

	public UserParameter toParameter() {
		UserParameter parameter = new UserParameter();
		parameter.setCookieStore(new BasicCookieStore());
		parameter.setPassword(password);
		parameter.setUsername(username);
		parameter.setWechatId(wechatId);
		parameter.setSubject(userInfo.getSubject());
		return parameter;
	}

	public UserParameter updateParameter(UserParameter parameter) {
		parameter.setPassword(this.password);
		parameter.setUsername(this.username);
		parameter.setWechatId(this.wechatId);
		parameter.setSubject(this.userInfo.getSubject());
		return parameter;
	}

	/**
	 * 检查token是否失效
	 * 
	 * @return true 未失效 ； false 失效
	 */
	public boolean checkToken() {
		long time = System.currentTimeMillis();
		if (userToken == null) {
			userToken = new UserToken();
			return false;
		} else {
			if (time - userToken.getScoreToken() > 5 * 60 * 1000)
				return false;
			else {
				return true;
			}
		}
	}

	/**
	 * 更新ScoreToken
	 */
	public void updateToken() {
		if (userToken == null)
			userToken = new UserToken();
		userToken.setScoreToken(System.currentTimeMillis());
	}
}
