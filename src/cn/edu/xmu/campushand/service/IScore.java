package cn.edu.xmu.campushand.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import cn.edu.xmu.campushand.displaymodel.ScoreEntry;
import cn.edu.xmu.campushand.exceptions.NetworkException;
import cn.edu.xmu.campushand.parameter.UserParameter;

/**
 * 
 * @author Wo
 * 
 */
public interface IScore {
	/**
	 * 获取本学期成绩
	 * 
	 * @param userParameter
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NetworkException
	 */
	public String getScore(UserParameter userParameter)
			throws ClientProtocolException, IOException, NetworkException;

	/**
	 * 网页版调用
	 * 
	 * @param userParameter
	 * @return 成功时返回非null，失败返回null
	 * 
	 * @throws NetworkException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public List<ScoreEntry> getScoreEntries(UserParameter userParameter)
			throws NetworkException, ClientProtocolException, IOException;

	/**
	 * 用于计算该用户的GPA
	 * 
	 * @param userParameter
	 *            有CookieStore和GPAurl
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String calculateGPA(UserParameter userParameter)
			throws ClientProtocolException, IOException;

	/**
	 * 用于查询方案完成情况
	 * 
	 * @param userParameter
	 *            包含有用户信息的参数：cookiestore用于继续访问该网站
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String queryAccomplishment(UserParameter userParameter)
			throws ClientProtocolException, IOException;
}
