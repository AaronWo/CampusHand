package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xmu.campushand.displaymodel.ScoreEntry;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IScore;

/**
 * 
 * @author Wo
 * 
 *         已测试
 * 
 *         已处理异常
 * 
 */
public class XMUScoreService implements IScore {

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException {
		HttpClient httpClient = HttpClients.custom().build();
		HttpClientContext context = HttpClientContext.create();

		if (userParameter.getCookieStore() == null)
			userParameter.setCookieStore(new BasicCookieStore());

		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet(
				"http://ssfw.xmu.edu.cn/cmstar/index.portal?.pn=p1201_p3535");
		HttpResponse response = httpClient.execute(httpGet, context);

		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			parseHTML(response);
		} else {
			throw new NetworkException("网络异常");
		}

		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public String parseHTML(HttpResponse response) throws ParseException,
			IOException {
		String result = "";
		Document document = Jsoup.parse(EntityUtils.toString(response
				.getEntity()));
		Elements elements = document.select("tr");
		for (Element e : elements) {
			if (e.getAllElements().size() == 15) {
				Elements tdElements = e.select("td");
				System.out.println(tdElements.get(0).text() + " "
						+ tdElements.get(1).text() + " "
						+ tdElements.get(4).text());
				System.out.println();
			}
		}
		return result;
	}

	@Override
	public String calculateGPA(UserParameter userParameter)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAccomplishment(UserParameter userParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScoreEntry> getScoreEntries(UserParameter userParameter) {
		// TODO Auto-generated method stub
		return null;
	}

}
