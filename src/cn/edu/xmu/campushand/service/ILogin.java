package cn.edu.xmu.campushand.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import cn.edu.xmu.campushand.exceptions.BandFailException;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 
 * @author Wo
 * 
 */
public interface ILogin {
	public boolean login(UserParameter userParameter)
			throws ClientProtocolException, IOException, LoginFailException,
			NetworkException;

	public void band(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException,
			LoginFailException, BandFailException;
}
