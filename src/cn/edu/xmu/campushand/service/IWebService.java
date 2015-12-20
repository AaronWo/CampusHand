package cn.edu.xmu.campushand.service;

/**
 * 2014-04-19
 * 
 * @author Wo
 * 
 */
public interface IWebService {
	/**
	 * 查询城市天气的webservice调用
	 * 
	 * @param cityName
	 *            城市名称
	 * @return 包含该城市天气信息的字符串
	 */
	public String getWeather(String cityName);
}
