package cn.edu.xmu.campushand.displaymodel;

import java.io.Serializable;

/**
 * 用来将数据传给网页
 * 
 * @author Wo
 * 
 */
public class ScoreEntry implements Serializable {

	private static final long serialVersionUID = 9188312897205720901L;

	private String name;
	private String type;
	private String score;

	/**
	 * 构造函数
	 * 
	 * @param n
	 *            名称
	 * @param t
	 *            类型
	 * @param s
	 *            分数
	 */
	public ScoreEntry(String n, String t, String s) {
		name = n;
		type = t;
		score = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return name + "\t" + type + "\t" + score + "\n";
	}
}
