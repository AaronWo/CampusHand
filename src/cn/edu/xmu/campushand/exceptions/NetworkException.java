package cn.edu.xmu.campushand.exceptions;

/**
 * 教务系统出问题时抛出的异常
 * 
 * @author Wo
 * 
 */
public class NetworkException extends Exception {

	private static final long serialVersionUID = 7373145549186279509L;

	public NetworkException() {
	}

	public NetworkException(String message) {
		super(message);
	}
}
