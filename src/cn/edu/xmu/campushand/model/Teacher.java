package cn.edu.xmu.campushand.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * 
 * @author Wo
 * 
 */
@Entity
public class Teacher implements Serializable {

	private static final long serialVersionUID = -2245508022981326459L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Version
	@Column(name = "OPTLOCK")
	private int version;

	private String name;

	private String sex;

	private String department; // 学院

	@OneToMany
	@Cascade(value = CascadeType.ALL)
	private List<Lecture> lectures;

	@OneToOne
	private TeacherLectureRemark remark;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}

	public TeacherLectureRemark getRemark() {
		return remark;
	}

	public void setRemark(TeacherLectureRemark remark) {
		this.remark = remark;
	}

}
