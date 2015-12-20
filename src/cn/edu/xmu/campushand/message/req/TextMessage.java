package cn.edu.xmu.campushand.message.req;

/**
 * 文本消息
 * 
 * @author liufeng
 * @date 2013-05-19
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
