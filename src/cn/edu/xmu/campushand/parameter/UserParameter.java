package cn.edu.xmu.campushand.parameter;

import java.io.Serializable;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * 用于方法调用时参数的传递
 * 
 * @author Wo
 * 
 */
public class UserParameter implements Serializable {

	private static final long serialVersionUID = 2967003548540872298L;

	private String username;
	private String password;
	private BasicCookieStore cookieStore;
	private String wechatId;
	private String university;
	private String subject;
	private String sex;
	private String name;
	private int term;
	/**
	 * 用来记录通过连接访问的用户的openid
	 */
	private String openid;
	/**
	 * 用户记录访问是否过期
	 */
	private long token;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}

	/**
	 * 该专业学生查询GPA的URL
	 */
	private String gpaUrl;

	public String getGpaUrl() {
		return gpaUrl;
	}

	public void setGpaUrl(String gpaUrl) {
		this.gpaUrl = gpaUrl;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	private HttpResponse response; // login response

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
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

	public BasicCookieStore getCookieStore() {
		return cookieStore;
	}

	public void setCookieStore(BasicCookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

}
