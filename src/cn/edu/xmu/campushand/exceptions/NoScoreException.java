package cn.edu.xmu.campushand.exceptions;

/**
 * 该学期暂无成绩时抛出的异常
 * 
 * @author Wo
 * 
 */
public class NoScoreException extends Exception {
	private static final long serialVersionUID = 8020075037105877527L;

	public NoScoreException() {
	}

	public NoScoreException(String msg) {
		super(msg);
	}
}
