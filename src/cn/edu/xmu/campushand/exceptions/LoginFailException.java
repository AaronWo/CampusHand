package cn.edu.xmu.campushand.exceptions;

/**
 * 登录失败时抛出的异常
 * 
 * @author Wo
 * 
 */
public class LoginFailException extends Exception {

	private static final long serialVersionUID = -1321444080868941832L;

	public LoginFailException() {
	}

	public LoginFailException(String message) {
		super(message);
	}
}
