package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xmu.campushand.dao.UserInfoDao;
import cn.edu.xmu.campushand.dao.XMUUserDao;
import cn.edu.xmu.campushand.exceptions.BandFailException;
import cn.edu.xmu.campushand.exceptions.LoginFailException;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.model.UserInfo;
import cn.edu.xmu.campushand.model.XMUUser;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.ILogin;

/**
 * 
 * @author Wo
 * 
 *         测试已完成
 * 
 *         已加入异常处理
 * 
 */

public class XMULoginService implements ILogin {

	@Autowired
	@Qualifier("userInfoDao")
	private UserInfoDao userInfoDao;

	@Autowired
	@Qualifier("xmuUserDao")
	private XMUUserDao xmuUserDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public XMUUserDao getXmuUserDao() {
		return xmuUserDao;
	}

	public void setXmuUserDao(XMUUserDao xmuUserDao) {
		this.xmuUserDao = xmuUserDao;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean login(UserParameter userParameter) throws NetworkException,
			LoginFailException {

		if (userParameter.getCookieStore() == null)
			userParameter.setCookieStore(new BasicCookieStore());

		HttpClient httpClient = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();

		try {
			getSessionId(userParameter);
			context.setCookieStore(userParameter.getCookieStore());

			HttpEntity httpEntity = setPostParameter(userParameter);

			HttpPost httpPost = new HttpPost(
					"http://i.xmu.edu.cn/userPasswordValidate.portal");
			httpPost.setEntity(httpEntity);

			HttpResponse response = httpClient.execute(httpPost, context);
			if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
				setLoginCookie(response, userParameter);
				userParameter.setResponse(response);
				return true;
			} else {
				throw new LoginFailException("登陆失败");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			throw e;
		} catch (LoginFailException e) {
			throw e;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private String getCookieString(BasicCookieStore cStore) {
		String cString = "";
		List<Cookie> cookies = cStore.getCookies();
		for (Cookie c : cookies) {
			cString += c.getName() + "=" + c.getValue() + ";";
		}
		System.out.println("  Cookie:  " + cString);
		return cString;
	}

	/**
	 * 设置Post的参数
	 * 
	 * @param userParameter
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private HttpEntity setPostParameter(UserParameter userParameter)
			throws UnsupportedEncodingException {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("Login.Token1", userParameter
				.getUsername()));
		params.add(new BasicNameValuePair("Login.Token2", userParameter
				.getPassword()));
		params.add(new BasicNameValuePair("goto",
				"http%3A%2F%2Fi.xmu.edu.cn%2FloginSuccess.portal"));
		params.add(new BasicNameValuePair("gotoOnFail",
				"http%3A%2F%2Fi.xmu.edu.cn%2FloginFailure.portal"));
		HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");

		return httpEntity;
	}

	private void setLoginCookie(HttpResponse response,
			UserParameter userParameter) {
		Header[] headers = response.getAllHeaders();
		for (Header h : headers) {
			System.out.println("header : " + h.getName() + ":" + h.getValue());
		}
	}

	private void getSessionId(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException {

		if (userParameter.getCookieStore() == null)
			userParameter.setCookieStore(new BasicCookieStore());

		HttpClient httpClient = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();

		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet("http://i.xmu.edu.cn/index.portal");
		HttpResponse response = httpClient.execute(httpGet, context);
		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			Header[] headers = response.getAllHeaders();
			for (Header h : headers) {
				System.out.println("header : " + h.getName() + ":"
						+ h.getValue());
			}
		} else {
			throw new NetworkException("网络异常");
		}

	}

	/**
	 * 绑定账号 存储XMUUser 和 UserInfo
	 * 
	 * @throws NetworkException
	 * @throws LoginFailException
	 * @throws BandFailException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void band(UserParameter userParameter) throws NetworkException,
			BandFailException, LoginFailException {

		if (userParameter.getCookieStore() == null)
			userParameter.setCookieStore(new BasicCookieStore());

		HttpClient httpClient = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();

		context.setCookieStore(userParameter.getCookieStore());

		HttpResponse response;
		try {
			response = getLoginResponse(userParameter);
			if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
				HttpGet httpGet = new HttpGet(
						"http://i.xmu.edu.cn/index.portal");

				response = httpClient.execute(httpGet, context);

				if (HttpStatus
						.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
					Document document = Jsoup.parse(EntityUtils
							.toString(response.getEntity()));

					Element element = document.select("div.composer").first();
					Elements liElements = element.children().first().children();

					UserInfo userInfo = createUserInfo(liElements);
					userInfoDao.insert(userInfo);

					XMUUser xmuUser = createXmuUser(userParameter);
					xmuUser.setUserInfo(userInfo);
					xmuUserDao.insert(xmuUser);

				} else {
					throw new NetworkException("网络异常");
				}

			} else {
				throw new BandFailException("绑定失败");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			throw e;
		} catch (BandFailException e) {
			throw e;
		}

	}

	private HttpResponse getLoginResponse(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException,
			BandFailException {
		HttpClient httpClient = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();

		try {
			getSessionId(userParameter);
			context.setCookieStore(userParameter.getCookieStore());

			HttpEntity httpEntity = setPostParameter(userParameter);

			HttpPost httpPost = new HttpPost(
					"http://i.xmu.edu.cn/userPasswordValidate.portal");
			httpPost.setEntity(httpEntity);

			HttpResponse response = httpClient.execute(httpPost, context);
			if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
				if (EntityUtils.toString(response.getEntity()).contains(
						"用户不存在或密码错误")) {
					throw new BandFailException("账号密码错误");
				}
				return response;
			} else {
				throw new NetworkException("网络异常");
			}
		} catch (NetworkException e) {
			// 网络异常
			throw e;
		} catch (BandFailException e) {
			throw e;
		}

	}

	private String getName(Element element) {
		String name = element.text().trim()
				.subSequence(0, element.text().trim().indexOf(" ，")).toString();
		return name;
	}

	private String getSubject(Element element) {
		String subject = element.text().trim()
				.substring(element.text().trim().indexOf("：") + 1).toString();
		return subject;
	}

	private UserInfo createUserInfo(Elements liElements) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(getName(liElements.get(0)));
		userInfo.setUniversity("厦门大学");
		userInfo.setSubject(getSubject(liElements.get(2)));
		return userInfo;
	}

	private XMUUser createXmuUser(UserParameter userParameter) {
		XMUUser xmuUser = new XMUUser();
		xmuUser.setPassword(userParameter.getPassword());
		xmuUser.setUsername(userParameter.getUsername());
		xmuUser.setWechatId(userParameter.getWechatId());
		return xmuUser;
	}
}
