package cn.edu.xmu.campushand.exceptions;

/**
 * 
 * 查询成绩时间过长时抛出的异常
 * 
 * @author Wo
 * 
 */
public class TokenExpairException extends Exception {

	private static final long serialVersionUID = 4746013442648734202L;

	public TokenExpairException() {
	}

	public TokenExpairException(String msg) {
		super(msg);
	}
}
