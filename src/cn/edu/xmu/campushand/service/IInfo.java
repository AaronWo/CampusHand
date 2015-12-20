package cn.edu.xmu.campushand.service;

import java.io.IOException;

import org.apache.http.ParseException;

import cn.edu.xmu.campushand.model.UserInfo;
import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 
 * @author Wo
 * 
 */
public interface IInfo {

	public UserInfo getInfo(UserParameter userParameter) throws ParseException,
			IOException;

}
