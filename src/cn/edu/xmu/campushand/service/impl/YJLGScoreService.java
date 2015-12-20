package cn.edu.xmu.campushand.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;

import cn.edu.xmu.campushand.dao.YJLGMajorDao;
import cn.edu.xmu.campushand.displaymodel.ScoreEntry;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.model.Course;
import cn.edu.xmu.campushand.model.YJLGMajor;
import cn.edu.xmu.campushand.parameter.UserParameter;
import cn.edu.xmu.campushand.service.IScore;
import cn.edu.xmu.campushand.util.ConstUtil;

/**
 * 
 * @author Wo
 * 
 *         测试已完成
 * 
 *         已处理异常
 * 
 */
public class YJLGScoreService implements IScore {

	static Logger logger = Logger.getLogger(YJLGInfoService.class);

	@Autowired
	@Qualifier("yjlgMajorDao")
	private YJLGMajorDao yjlgMajorDao;

	public YJLGMajorDao getYjlgMajorDao() {
		return yjlgMajorDao;
	}

	public void setYjlgMajorDao(YJLGMajorDao yjlgMajorDao) {
		this.yjlgMajorDao = yjlgMajorDao;
	}

	@Override
	public String getScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException {

		String result = "";

		HttpClient httpClient = HttpClients.custom().build();

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet(
				"http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=lnFajhKcCjInfo");
		HttpResponse response = httpClient.execute(httpGet, context);
		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			Document document = Jsoup.parse(EntityUtils.toString(response
					.getEntity()));

			logger.info(document);

			Elements termElements = document.select("table[class=title]");

			switch (userParameter.getTerm()) {
			case ConstUtil.YEAR_ONE_1:
				result = getTermOne1(termElements);
				break;
			case ConstUtil.YEAR_ONE_2:
				result = getTermOne2(termElements);
				break;
			case ConstUtil.YEAR_TWO_1:
				result = getTermTwo1(termElements);
				break;
			case ConstUtil.YEAR_TWO_2:
				result = getTermTwo2(termElements);
				break;
			case ConstUtil.YEAR_THREE_1:
				result = getTermThree1(termElements);
				break;
			case ConstUtil.YEAR_THREE_2:
				result = getTermThree2(termElements);
				break;
			case ConstUtil.YEAR_FOUR_1:
				result = getTermFour1(termElements);
				break;
			case ConstUtil.YEAR_FOUR_2:
				result = getTermFour2(termElements);
				break;
			default:
				break;
			}
		} else {
			throw new NetworkException("网络异常");
		}
		if ("".equals(result))
			result = "暂无成绩！\n";
		logger.info(result);
		return result;
	}

	/**
	 * 大一上
	 * 
	 * @param tElements
	 * @return
	 */
	private String getTermOne1(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 1) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 2) {
					courseElements.remove(0);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @param termElements
	 * @param term
	 *            1~8
	 * @return
	 */
	private List<ScoreEntry> getTerm_web(Elements termElements, int term) {
		int flag = 0;
		String result = "";
		List<ScoreEntry> list = new ArrayList<ScoreEntry>();
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 2 * term - 1) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
						list.add(new ScoreEntry(sonElements.get(0).text()
								.trim().substring(2), sonElements.get(1).text()
								.trim().substring(2), sonElements.get(2).text()
								.trim().substring(2)));
					}
				}
				if (flag == 2 * term) {
					courseElements.remove(0);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
						list.add(new ScoreEntry(sonElements.get(0).text()
								.trim(), sonElements.get(1).text().trim(),
								sonElements.get(2).text().trim()));
					}
					break;
				}
			}
		}
		logger.info(result);
		for (ScoreEntry se : list)
			logger.info(se);
		return list;
	}

	/**
	 * 大一下
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermOne2(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 3) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 4) {
					courseElements.remove(0);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 大二上
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermTwo1(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 5) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 6) {
					courseElements.remove(0);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 大二下
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermTwo2(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 7) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 8) {
					courseElements.remove(0);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 大三上
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermThree1(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 9) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 10) {
					courseElements.remove(0);
					courseElements.remove(0);

					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 大三下
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermThree2(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 11) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 12) {
					courseElements.remove(0);
					courseElements.remove(0);

					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 大四上
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermFour1(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 13) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 14) {
					courseElements.remove(0);
					courseElements.remove(0);

					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 大三上
	 * 
	 * @param termElements
	 * @return
	 */
	private String getTermFour2(Elements termElements) {
		int flag = 0;
		String result = "";
		for (Element element : termElements) {
			if (element.text().trim().contains("成绩")
					&& element.text().trim().contains("学年")
					&& element.text().trim().contains("学期")) {
				flag++;
				Element targetElement = element.nextElementSibling()
						.nextElementSibling();
				Elements courseElements = targetElement.select("tr");
				if (flag == 15) {
					courseElements.remove(0);
					courseElements.remove(courseElements.size() - 1);
					courseElements.remove(0);
					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim().substring(2)
								+ "  "
								+ sonElements.get(1).text().trim().substring(2)
								+ "  "
								+ sonElements.get(2).text().trim().substring(2)
								+ "\n";
					}
				}
				if (flag == 16) {
					courseElements.remove(0);
					courseElements.remove(0);

					for (Element ee : courseElements) {
						Elements sonElements = ee.children();
						sonElements.remove(0);
						result += sonElements.get(0).text().trim() + "  "
								+ sonElements.get(1).text().trim() + "  "
								+ sonElements.get(2).text().trim() + "\n";
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 计算GPA
	 * 
	 * @param userParameter
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String calculateGPA(UserParameter userParameter)
			throws ClientProtocolException, IOException {
		String result = "";
		YJLGMajor major = yjlgMajorDao.queryByMajor(userParameter.getSubject());
		String gpaUrl = "";
		if (major == null || major.getGpaUrl() == null
				|| major.getGpaUrl().equals("")) {
			if (major == null) {
				major = new YJLGMajor();
				yjlgMajorDao.insert(major);
			}
			major.setMajorName(userParameter.getSubject());
			gpaUrl = getGpaUrl(userParameter);
			major.setGpaUrl(gpaUrl);
			yjlgMajorDao.update(major);
		} else {
			gpaUrl = major.getGpaUrl();
		}
		HttpClient httpClient = HttpClients.custom().build();

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet(gpaUrl);
		HttpResponse response = httpClient.execute(httpGet, context);

		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			Document document = Jsoup.parse(EntityUtils.toString(response
					.getEntity()));
			Elements trElements = document.select("tr[class=odd]");
			List<Course> courseList = new ArrayList<Course>();
			for (Element course : trElements) {
				Elements elements = course.children();
				Course c = new Course(
						elements.get(2).text().trim(),
						elements.get(4).text().trim(),
						elements.get(5).text().trim(),
						elements.get(6)
								.text()
								.trim()
								.substring(
										0,
										elements.get(6).text().trim().length() - 1));
				courseList.add(c);
			}
			result = "您的GPA为：" + calculateGPAValue1(courseList);
			logger.info(result);
		}
		return result;
	}

	/**
	 * 厦大算法（国际算法）
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unused")
	private String calculateGPAValue(List<Course> list) {
		double sumCreditValue = 0;
		double gpaValue = 0;
		double tempScore;
		for (Course c : list) {
			sumCreditValue += c.getCreditValue();
			if (isNum(c.getScore())) {
				tempScore = Double.parseDouble(c.getScore());
				if (tempScore < 0)
					gpaValue += 0 * c.getCreditValue();
				else if (tempScore >= 60 && tempScore <= 63)
					gpaValue += 1 * c.getCreditValue();
				else if (tempScore >= 64 && tempScore <= 67)
					gpaValue += 1.7 * c.getCreditValue();
				else if (tempScore >= 68 && tempScore <= 71)
					gpaValue += 2 * c.getCreditValue();
				else if (tempScore >= 72 && tempScore <= 74)
					gpaValue += 2.3 * c.getCreditValue();
				else if (tempScore >= 75 && tempScore <= 77)
					gpaValue += 2.7 * c.getCreditValue();
				else if (tempScore >= 78 && tempScore <= 80)
					gpaValue += 3.0 * c.getCreditValue();
				else if (tempScore >= 81 && tempScore <= 84)
					gpaValue += 3.3 * c.getCreditValue();
				else if (tempScore >= 85 && tempScore <= 89)
					gpaValue += 3.7 * c.getCreditValue();
				else if (tempScore >= 90)
					gpaValue += 4.0 * c.getCreditValue();
			} else {
				if (c.getScore().equals("优"))
					gpaValue += 4.0 * c.getCreditValue();
				else if (c.getScore().equals("良"))
					gpaValue += 3.0 * c.getCreditValue();
				else if (c.getScore().equals("中"))
					gpaValue += 2.0 * c.getCreditValue();
				else if (c.getScore().endsWith("及格"))
					gpaValue += 1.3 * c.getCreditValue();
				// 合格不算在内
				else if (c.getScore().equals("合格"))
					sumCreditValue -= c.getCreditValue();
				else {
					gpaValue += 0;
				}
			}
		}
		String result;
		if (sumCreditValue < 0.00001)
			result = "无法计算GPA";
		else {
			result = String.format("%.2f", (gpaValue / sumCreditValue));
		}
		return result;
	}

	/**
	 * 燕京理工算法（5分制）
	 * 
	 * @param list
	 * @return
	 */
	private String calculateGPAValue1(List<Course> list) {

		double sumCreditValue = 0;
		double gpaValue = 0;
		double tempScore;
		for (Course c : list) {
			sumCreditValue += c.getCreditValue();
			if (isNum(c.getScore())) {
				tempScore = Double.parseDouble(c.getScore());
				if (tempScore < 60)
					gpaValue += 0 * c.getCreditValue();
				else if (tempScore == 60)
					gpaValue += 1 * c.getCreditValue();
				else if (tempScore >= 61 && tempScore <= 63)
					gpaValue += 1 * c.getCreditValue();
				else if (tempScore >= 64 && tempScore <= 66)
					gpaValue += 1.67 * c.getCreditValue();
				else if (tempScore >= 67 && tempScore <= 69)
					gpaValue += 2 * c.getCreditValue();
				else if (tempScore >= 70 && tempScore <= 73)
					gpaValue += 2.33 * c.getCreditValue();
				else if (tempScore >= 74 && tempScore <= 76)
					gpaValue += 2.67 * c.getCreditValue();
				else if (tempScore >= 77 && tempScore <= 79)
					gpaValue += 3.0 * c.getCreditValue();
				else if (tempScore >= 80 && tempScore <= 83)
					gpaValue += 3.33 * c.getCreditValue();
				else if (tempScore >= 84 && tempScore <= 86)
					gpaValue += 3.67 * c.getCreditValue();
				else if (tempScore >= 87 && tempScore <= 89)
					gpaValue += 4 * c.getCreditValue();
				else if (tempScore >= 90 && tempScore <= 94)
					gpaValue += 4.33 * c.getCreditValue();
				else if (tempScore >= 95 && tempScore <= 100)
					gpaValue += 4.67 * c.getCreditValue();
			} else {
				if (c.getScore().equals("优"))
					gpaValue += 4.33 * c.getCreditValue();
				else if (c.getScore().equals("良"))
					gpaValue += 3.33 * c.getCreditValue();
				else if (c.getScore().equals("中"))
					gpaValue += 2.33 * c.getCreditValue();
				else if (c.getScore().endsWith("及格"))
					gpaValue += 1.0 * c.getCreditValue();
				// 合格不算在内
				else if (c.getScore().equals("合格"))
					sumCreditValue -= c.getCreditValue();
				else {
					gpaValue += 0;
				}
			}
		}
		String result;
		if (sumCreditValue < 0.00001)
			result = "无法计算GPA";
		else {
			result = String.format("%.2f", (gpaValue / sumCreditValue));
		}
		return result;

	}

	/**
	 * 判断成绩是否为浮点数
	 * 
	 * @param str
	 *            输入的字符串
	 * @return 布尔变量，是否为浮点数；是-1；否-0
	 */
	private boolean isNum(String str) {
		Pattern pattern = Pattern.compile("[0-9]*.[0-9]+");
		return pattern.matcher(str).matches();
	}

	@Override
	public String queryAccomplishment(UserParameter userParameter)
			throws ClientProtocolException, IOException {
		String result = "培养方案完成情况：\n";
		String url = getAccompolishmentUrl(userParameter);
		/*
		 * YJLGMajor major =
		 * yjlgMajorDao.queryByMajor(userParameter.getSubject()); if (major ==
		 * null || major.getAccomplishmentUrl() == null ||
		 * major.getAccomplishmentUrl().equals("")) { if (major == null) { major
		 * = new YJLGMajor(); major.setMajorName(userParameter.getSubject());
		 * yjlgMajorDao.insert(major); }
		 * major.setAccomplishmentUrl(getAccompolishmentUrl(userParameter)); url
		 * = major.getAccomplishmentUrl(); yjlgMajorDao.update(major); } else {
		 * url = major.getAccomplishmentUrl(); }
		 */
		if (!url.equals("http://219.226.183.105:8080/")) {
			HttpClient httpClient = HttpClients.custom().build();

			HttpClientContext context = HttpClientContext.create();
			context.setCookieStore(userParameter.getCookieStore());

			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet, context);
			if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
				Document document = Jsoup.parse(EntityUtils.toString(response
						.getEntity()));
				Element table = document.select("table[id=tblView]").first();
				if (table != null) {
					Elements trs = table.select("tr");
					for (Element tr : trs) {
						if (!tr.text().trim().equals(""))
							result += tr.text().trim() + "\n";
						else if (tr.text().contains("完成情况")) {
							result += "\n" + tr.text().trim();
						}
					}
					logger.info(result);
				}
			}
		}
		return result;
	}

	/**
	 * 根据登陆返回的正文部分，分析html，从iframe中得到网址
	 * 
	 * @param userParameter
	 *            有包含登陆信息的CookieStore
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private String getAccompolishmentUrl(UserParameter userParameter)
			throws ClientProtocolException, IOException {
		String result = "http://219.226.183.105:8080/";
		HttpClient httpClient = HttpClients.custom().build();

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet(
				"http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=lnfaqk&flag=zx");
		HttpResponse response = httpClient.execute(httpGet, context);
		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			Document document = Jsoup.parse(EntityUtils.toString(response
					.getEntity()));
			Element iframe = document.select("iframe[name=lnfaqkIfra]").first();
			if (iframe != null) {
				result += iframe.attr("src");
			}
		}
		return result;
	}

	/**
	 * 
	 * 根据登陆返回的正文部分，分析html，从iframe中得到网址
	 * 
	 * @param userParameter
	 *            有包含登陆信息的CookieStore
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	private String getGpaUrl(UserParameter userParameter)
			throws ClientProtocolException, IOException {
		String result = "http://219.226.183.105:8080/";
		HttpClient httpClient = HttpClients.custom().build();

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet(
				"http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=fa");
		HttpResponse response = httpClient.execute(httpGet, context);
		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			Document document = Jsoup.parse(EntityUtils.toString(response
					.getEntity()));
			Element iframe = document.select("iframe[name=lnfaIfra]").first();
			if (iframe != null) {
				result += iframe.attr("src");
			}
		}
		return result;
	}

	@Override
	public List<ScoreEntry> getScoreEntries(UserParameter userParameter)
			throws NetworkException, ClientProtocolException, IOException {

		List<ScoreEntry> list = null;
		HttpClient httpClient = HttpClients.custom().build();

		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(userParameter.getCookieStore());

		HttpGet httpGet = new HttpGet(
				"http://219.226.183.105:8080/gradeLnAllAction.do?type=ln&oper=lnFajhKcCjInfo&lnxndm=2013-2014学年第一学期(两学期)");
		HttpResponse response = httpClient.execute(httpGet, context);
		if (HttpStatus.valueOf(response.getStatusLine().getStatusCode()) == HttpStatus.OK) {
			Document document = Jsoup.parse(EntityUtils.toString(response
					.getEntity()));
			Elements termElements = document.select("table[class=title]");

			switch (userParameter.getTerm()) {
			case ConstUtil.YEAR_ONE_1:
				list = getTerm_web(termElements, 1);
				break;
			case ConstUtil.YEAR_ONE_2:
				list = getTerm_web(termElements, 2);
				break;
			case ConstUtil.YEAR_TWO_1:
				list = getTerm_web(termElements, 3);
				break;
			case ConstUtil.YEAR_TWO_2:
				list = getTerm_web(termElements, 4);
				break;
			case ConstUtil.YEAR_THREE_1:
				list = getTerm_web(termElements, 5);
				break;
			case ConstUtil.YEAR_THREE_2:
				list = getTerm_web(termElements, 6);
				break;
			case ConstUtil.YEAR_FOUR_1:
				list = getTerm_web(termElements, 7);
				break;
			case ConstUtil.YEAR_FOUR_2:
				list = getTerm_web(termElements, 8);
				break;
			default:
				break;
			}
		} else {
			throw new NetworkException("网络异常");
		}
		return list;
	}
}
