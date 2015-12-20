package cn.edu.xmu.campushand.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import cn.edu.xmu.campushand.message.resp.Article;
import cn.edu.xmu.campushand.message.resp.NewsMessage;
import cn.edu.xmu.campushand.message.resp.TextMessage;
import cn.edu.xmu.campushand.service.IChatRobot;
import cn.edu.xmu.campushand.util.MessageUtil;

/**
 * 处理由机器人负责的请求Service
 * 
 * @author Wo
 * 
 */
public class ChatRobotService implements IChatRobot {

	private Map<String, String> requestMap;

	static Logger logger = Logger.getLogger(ChatRobotService.class);

	/**
	 * 处理由机器人处理的聊天请求
	 */
	@Override
	public String responseChat(Map<String, String> requestMap, String chat) {
		logger.info("聊天请求");

		this.requestMap = requestMap;
		// 读取结果网页
		HttpClient httpClient = HttpClients.custom().build();// 创建httpClient对象
		String finalRes = null;
		try {
			String INFO = URLEncoder.encode(chat, "utf-8");
			String url = "http://www.tuling123.com/openapi/api?key=ebfb5a014f7b2bd1107297ddee310d43&info="
					+ INFO;
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				finalRes = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = JSONObject.fromString(finalRes);
				logger.info(jsonObject);
				return processResponse(jsonObject);
			} else {
				logger.info(response);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		TextMessage textMessage = ControlService.createTextMessage(requestMap);
		textMessage.setContent("网络繁忙,小小已经忙不过来了呢!");
		return MessageUtil.textMessageToXml(textMessage);
	}

	/**
	 * 进一步分发请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processResponse(JSONObject jsonObject) {
		final int code = jsonObject.getInt("code");
		switch (code) {
		case 100000:
			return processText(jsonObject);
		case 200000:
			return processLinks(jsonObject);
		case 301000:
			return processNoval(jsonObject);
		case 302000:
			return processNews(jsonObject);
		case 305000:
			return processTrain(jsonObject);
		case 306000:
			return processPlane(jsonObject);
		case 308000:
			return processMovie(jsonObject);
		case 309000:
			return processHotel(jsonObject);
		case 312000:
			return processRestaurant(jsonObject);
		default:
			return processOthers(jsonObject);
		}
	}

	/**
	 * 处理对话
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processText(JSONObject jsonObject) {
		logger.info("文本信息");
		if (requestMap != null) {
			TextMessage textMessage = ControlService
					.createTextMessage(requestMap);
			textMessage.setContent(jsonObject.getString("text"));
			return MessageUtil.textMessageToXml(textMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理链接
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processLinks(JSONObject jsonObject) {
		logger.info("链接信息");
		if (requestMap != null) {
			TextMessage textMessage = ControlService
					.createTextMessage(requestMap);
			String content = jsonObject.getString("text") + "<a href=\" "
					+ jsonObject.getString("url") + " \">：点这里</a>";
			textMessage.setContent(content);
			return MessageUtil.textMessageToXml(textMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理小说请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processNoval(JSONObject jsonObject) {
		logger.info("小说信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("name") + "\n" + "作者: "
						+ news.getString("author");
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}

	}

	/**
	 * 处理新闻请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processNews(JSONObject jsonObject) {
		logger.info("新闻信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("article");
				Article article = new Article();
				if (i == 0) {
					content = content + "\n来源: " + news.getString("source");
					article.setTitle(jsonObject.getString("text"));
				}
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理查询火车票的请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processTrain(JSONObject jsonObject) {
		logger.info("火车票信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("trainnum") + "\n"
						+ news.getString("start") + ": "
						+ news.getString("starttime") + "\n"
						+ news.getString("terminal") + ": "
						+ news.getString("endtime");
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理查询机票的请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processPlane(JSONObject jsonObject) {
		logger.info("飞机票信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("flight") + "\n"
						+ news.getString("route") + "\n" + "起飞时间: "
						+ news.getString("starttime") + "\n" + "着陆时间: "
						+ news.getString("endtime");
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理查询电影的请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processMovie(JSONObject jsonObject) {
		logger.info("电影信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("name") + "\n"
						+ news.getString("info");
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理查询旅馆的请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processHotel(JSONObject jsonObject) {
		logger.info("附近酒店信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("name") + "\n" + "价格："
						+ news.getString("price") + "\n舒适度："
						+ news.getString("satisfaction") + "\n数量："
						+ news.getString("count");
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理查询餐厅的请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processRestaurant(JSONObject jsonObject) {
		logger.info("附近美食信息");
		if (requestMap != null) {
			NewsMessage newsMessage = ControlService
					.createNewsMessage(requestMap);
			List<Article> articleList = new ArrayList<Article>();
			JSONArray list = jsonObject.getJSONArray("list");
			for (int i = 0; i < list.length() && i < 10; i++) {
				JSONObject news = (JSONObject) list.get(i);
				String content = news.getString("name") + "\n价格："
						+ news.getString("price");
				Article article = new Article();
				article.setDescription("");
				article.setPicUrl(news.getString("icon"));
				article.setTitle(content);
				article.setUrl(news.getString("detailurl"));
				articleList.add(article);
			}
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			return MessageUtil.newsMessageToXml(newsMessage);
		} else {
			return jsonObject.toString();
		}
	}

	/**
	 * 处理其他请求
	 * 
	 * @param jsonObject
	 * @return
	 */
	private String processOthers(JSONObject jsonObject) {
		logger.info("其他信息");
		if (requestMap != null) {
			TextMessage textMessage = ControlService
					.createTextMessage(requestMap);
			textMessage.setContent(jsonObject.getString("text"));
			return MessageUtil.textMessageToXml(textMessage);
		} else {
			return jsonObject.toString();
		}
	}
}
