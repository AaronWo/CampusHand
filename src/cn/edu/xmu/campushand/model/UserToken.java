package cn.edu.xmu.campushand.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 查询成绩时的Token类
 * 
 * @author Wo
 * 
 */
@Entity
public class UserToken implements Serializable {

	private static final long serialVersionUID = -7865396608321023359L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long scoreToken;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getScoreToken() {
		return scoreToken;
	}

	public void setScoreToken(long scoreToken) {
		this.scoreToken = scoreToken;
	}

}
