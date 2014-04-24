package com.csair.ued.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 监控系统DTO
 * @author caiwei
 *
 */
@XmlRootElement(name = "monitor")
public class Monitor {
	//系统名称
	private String systemName;
	//刷新时间
	private String refreshTimestamp;
	//状态
	private String status;
	//声音文件名
	private String mp3;
	//监控url
	private String url;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getRefreshTimestamp() {
		return refreshTimestamp;
	}

	public void setRefreshTimestamp(String refreshTimestamp) {
		this.refreshTimestamp = refreshTimestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMp3() {
		return mp3;
	}

	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
