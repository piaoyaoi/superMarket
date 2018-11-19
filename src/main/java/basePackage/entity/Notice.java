package basePackage.entity;

import basePackage.BaseEntity;

/**
 * 公告类
 * @author piaoyao
 *
 */
public class Notice extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6816758250602829897L;
	/**
	 * 公告标题
	 */
	private String title;
	/**
	 * 公告消息
	 */
	private String noticeMsg;
	/**
	 * 公告权重
	 */
	private int weight;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNoticeMsg() {
		return noticeMsg;
	}
	public void setNoticeMsg(String noticeMsg) {
		this.noticeMsg = noticeMsg;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
}
