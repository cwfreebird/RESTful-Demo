package com.csair.ued.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 监控系统DTO
 * @author caiwei
 *
 */
@XmlRootElement(name = "monitors")
public class MonitorDTO {
	//监控信息列表
	private List<Monitor> monitor;

	public List<Monitor> getMonitor() {
		return monitor;
	}

	public void setMonitor(List<Monitor> monitor) {
		this.monitor = monitor;
	}
}
