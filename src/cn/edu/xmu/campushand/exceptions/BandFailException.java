package cn.edu.xmu.campushand.exceptions;

/**
 * 绑定失败时抛出的异常
 * 
 * @author Wo
 * 
 */
public class BandFailException extends Exception {

	private static final long serialVersionUID = 2594069582613337374L;

	public BandFailException() {
	}

	public BandFailException(String message) {
		super(message);
	}
}
