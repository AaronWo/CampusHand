package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import cn.edu.xmu.campushand.dao.UserInfoDao;
import cn.edu.xmu.campushand.dao.YJLGUserDao;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.model.UserInfo;
import cn.edu.xmu.campushand.model.YJLGUser;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.ILogin;

/**
 * 
 * @author Wo
 * 
 *         测试已完成
 * 
 *         已处理异常
 * 
 */
public class YJLGLoginService implements ILogin {

	static Logger logger = Logger.getLogger(YJLGInfoService.class);

	@Autowired
	@Qualifier("yjlgUserDao")
	private YJLGUserDao yjlgUserDao;

	@Autowired
	@Qualifier("userInfoDao")
	private UserInfoDao userInfoDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public YJLGUserDao getYjlgUserDao() {
		return yjlgUserDao;
	}

	public void setYjlgUserDao(YJLGUserDao yjlgUserDao) {
		this.yjlgUserDao = yjlgUserDao;
	}

	@Override
	public boolean login(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException,
			LoginFailException {

		try {
			getJsessionId(userParameter);
			HttpClient httpClient = HttpClients.custom().build();
			HttpClientContext httpClientContext = HttpClientContext.create();

			httpClientContext.setCookieStore(userParameter.getCookieStore());

			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("zjh", userParameter
					.getUsername()));
			params.add(new BasicNameValuePair("mm", userParameter.getPassword()));

			HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");

			HttpPost post = new HttpPost(
					"http://219.226.183.105:8080/loginAction.do");
			post.setEntity(entity);
			post.setHeader("Cookie",
					getCookieString(userParameter.getCookieStore()));

			HttpResponse response = httpClient.execute(post, httpClientContext);

			int status = response.getStatusLine().getStatusCode();

			logger.info(response);

			Header[] headers = response.getAllHeaders();
			for (Header h : headers) {
				logger.info(h);
			}

			String result = EntityUtils.toString(response.getEntity());
			logger.info(result);

			if (HttpStatus.valueOf(status) == HttpStatus.OK)
				return true;
			else
				throw new LoginFailException("登陆失败");
		} catch (NetworkException e) {
			throw e;
		} catch (LoginFailException e) {
			throw e;
		}

	}

	private String getCookieString(BasicCookieStore cStore) {
		String cString = "";
		List<Cookie> cookies = cStore.getCookies();
		for (Cookie c : cookies) {
			cString += c.getName() + "=" + c.getValue() + ";";
		}
		return cString;
	}

	private void getJsessionId(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException {
		if (userParameter.getCookieStore() == null)
			userParameter.setCookieStore(new BasicCookieStore());

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(userParameter.getCookieStore());

		HttpClient httpClient = HttpClients.custom().build();
		HttpGet get = new HttpGet("http://219.226.183.105:8080/");
		HttpResponse response = httpClient.execute(get, context);
		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
		} else {
			throw new NetworkException("网络异常");
		}
	}

	@Override
	public void band(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException,
			LoginFailException {
		HttpResponse response;
		try {
			response = getLoginResponse(userParameter);
			if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {

				HttpClient httpClient = HttpClients.custom().build();
				HttpClientContext context = HttpClientContext.create();
				context.setCookieStore(userParameter.getCookieStore());

				HttpGet httpGet = new HttpGet(
						"http://219.226.183.105:8080/xjInfoAction.do?oper=xjxx");
				response = httpClient.execute(httpGet, context);
				if (HttpStatus
						.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
					Document document = Jsoup.parse(EntityUtils
							.toString(response.getEntity()));
					Elements tdElements = document.select("td[width=275]");

					UserInfo userInfo = createUserInfo(tdElements);
					userInfoDao.insert(userInfo);
					YJLGUser yjlgUser = createYJLGUser(userParameter);
					yjlgUser.setUserInfo(userInfo);
					yjlgUserDao.insert(yjlgUser);
				} else {
					throw new NetworkException("网络异常");
				}

			} else {
				throw new LoginFailException("登陆失败");
			}
		} catch (NetworkException e) {
			throw e;
		} catch (LoginFailException e) {
			throw e;
		}

	}

	private HttpResponse getLoginResponse(UserParameter userParameter)
			throws NetworkException, LoginFailException {
		try {
			getJsessionId(userParameter);
			HttpClient httpClient = HttpClients.custom().build();
			HttpClientContext httpClientContext = HttpClientContext.create();

			httpClientContext.setCookieStore(userParameter.getCookieStore());

			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("zjh", userParameter
					.getUsername()));
			params.add(new BasicNameValuePair("mm", userParameter.getPassword()));

			HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");

			HttpPost post = new HttpPost(
					"http://219.226.183.105:8080/loginAction.do");
			post.setEntity(entity);
			post.setHeader("Cookie",
					getCookieString(userParameter.getCookieStore()));

			HttpResponse response = httpClient.execute(post, httpClientContext);
			if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
				if (EntityUtils.toString(response.getEntity()).contains(
						"请您重新输入！")) {
					throw new LoginFailException("登陆失败");
				}
				return response;
			} else {
				throw new NetworkException("网络异常");
			}
		} catch (ClientProtocolException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (NetworkException e) {
			logger.error(e);
			throw e;
		}
		return null;

	}

	private UserInfo createUserInfo(Elements tdElements) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(tdElements.get(1).text().trim());
		userInfo.setSex(tdElements.get(6).text().trim());
		userInfo.setUniversity("燕京理工");
		userInfo.setSubject(tdElements.get(26).text().trim());
		return userInfo;
	}

	private YJLGUser createYJLGUser(UserParameter userParameter) {
		YJLGUser yjlgUser = new YJLGUser();
		yjlgUser.setPassword(userParameter.getPassword());
		yjlgUser.setUsername(userParameter.getUsername());
		yjlgUser.setWechatId(userParameter.getWechatId());
		return yjlgUser;
	}
}
