package cn.edu.xmu.campushand.model;

import java.io.Serializable;

/**
 * 用于计算GPA 日后也可保存在数据库
 * 
 * @author Wo
 * 
 */
public class Course implements Serializable {

	private static final long serialVersionUID = -882846834064812843L;

	private long id;

	private String courseName;
	// 学分
	private double creditValue;

	private String score;

	private String category; // 必修？任选？

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public double getCreditValue() {
		return creditValue;
	}

	public void setCreditValue(double creditValue) {
		this.creditValue = creditValue;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Course(String name, String value, String category, String score) {
		this.courseName = name;
		this.creditValue = Double.parseDouble(value);
		this.category = category;
		this.score = score;
	}

	@Override
	public String toString() {
		return courseName + " " + creditValue + " " + category + " " + score;
	}
}
