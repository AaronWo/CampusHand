package cn.edu.xmu.campushand.message.req;

/**
 * 图片消息
 * 
 * @author liufeng
 * @date 2013-05-19
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String picUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
}