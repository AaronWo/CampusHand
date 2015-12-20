package cn.edu.xmu.campushand.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import cn.edu.xmu.campushand.displaymodel.ScoreEntry;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.exceptions.NoScoreException;
import cn.edu.xmu.campushand.exceptions.TokenExpairException;
import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 
 * @author Wo
 * 
 */
public interface IControlWebService {
	public List<ScoreEntry> scoreQuery(UserParameter userParameter)
			throws ClientProtocolException, IOException, LoginFailException,
			NetworkException, TokenExpairException, NoScoreException;
}
