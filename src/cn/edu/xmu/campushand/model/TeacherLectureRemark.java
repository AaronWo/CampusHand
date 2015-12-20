package cn.edu.xmu.campushand.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

/**
 * 
 * @author Wo
 * 
 */
@Entity
public class TeacherLectureRemark implements Serializable {

	private static final long serialVersionUID = 6974538316988078337L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Version
	@Column(name = "OPTLOCK")
	private int version;

	private double averageScore;

	private int times; // 评价次数

	@ElementCollection
	private List<String> contents;

	@OneToOne
	private Teacher teacher;

	@OneToOne
	private Lecture lecture;

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

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture lecture) {
		this.lecture = lecture;
	}

}
