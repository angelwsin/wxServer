package com.weixin.message.bean;

public class WXVideoMessage {
	/*MediaId	是	通过素材管理接口上传多媒体文件，得到的id
	Title	否	视频消息的标题
	Description	否	视频消息的描述*/
	
	private String MediaId;
	private String Title;
	private String Description;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
}
