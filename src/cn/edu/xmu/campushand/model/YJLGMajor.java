package cn.edu.xmu.campushand.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Wo
 * 
 *         燕京理工专业类
 * 
 */
@Entity
public class YJLGMajor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String majorName;
	private String gpaUrl;
	private String accomplishmentUrl;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getGpaUrl() {
		return gpaUrl;
	}

	public void setGpaUrl(String gpaUrl) {
		this.gpaUrl = gpaUrl;
	}

	public String getAccomplishmentUrl() {
		return accomplishmentUrl;
	}

	public void setAccomplishmentUrl(String accomplishmentUrl) {
		this.accomplishmentUrl = accomplishmentUrl;
	}

}
