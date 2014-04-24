package com.csair.ued.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.TestCase;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.csair.ued.dto.Monitor;
import com.csair.ued.dto.MonitorDTO;

public class MonitorServiceClient extends TestCase {
	private static final String URL = "http://localhost:8080/ued/services/";
	private static final String TYPE_XML = "application/xml";
	private static final String TYPE_JSON = "application/json";

	/**
	 * 增加单个监控点
	 */
	@Test
	public void testAddMonitor() {
		WebClient client = WebClient.create(URL);
		client.path("/monitorService/monitor").accept(TYPE_XML).type(TYPE_XML);

		Monitor Monitor = new Monitor();
		Monitor.setSystemName("TEST");
		Monitor.setMp3("MP3");
		Monitor.setRefreshTimestamp("2014-04-01 12:00:00");
		Monitor.setStatus("R");
		Monitor.setUrl("http://www.baidu.com");

		Monitor res = client.post(Monitor, Monitor.class);
		System.out.println(res.getSystemName());
	}
	
	/**
	 * 批量增加监控点
	 */
	@Test
	public void testAddMonitorList() {
		WebClient client = WebClient.create(URL);
		client.path("/monitorService/monitors").accept(TYPE_XML).type(TYPE_XML);

		MonitorDTO dto = new MonitorDTO();
		List<Monitor> list = new ArrayList<Monitor>();
		
		Monitor monitor = new Monitor();
		monitor.setSystemName("VOS");
		monitor.setMp3("VOS.MP3");
		monitor.setRefreshTimestamp("2014-04-01 12:00:00");
		monitor.setStatus("R");
		monitor.setUrl("http://www.baidu.com");
		
		list.add(monitor);
		
		Monitor monitor1 = new Monitor();
		monitor1.setSystemName("WEBCTI");
		monitor1.setMp3("WEBCTI.MP3");
		monitor1.setRefreshTimestamp("2014-04-01 12:00:00");
		monitor1.setStatus("R");
		monitor1.setUrl("http://www.baidu.com");

		list.add(monitor1);
		
		dto.setMonitor(list);
		MonitorDTO res = client.post(dto, MonitorDTO.class);
		System.out.println(res.getMonitor().size());
	}

	/**
	 * 根据系统名称 查询监控点
	 */
	@Test
	public void testGetMonitor() {
		WebClient client = WebClient.create(URL);
		Monitor res = client.path("/monitorService/monitor/TEST")
				.accept(TYPE_XML).type(TYPE_XML).get(Monitor.class);

		System.out.println(res.getSystemName());
	}
	
	/**
	 * 删除监控点
	 */
	@Test
	public void testDeleteMonitor() {
		WebClient client = WebClient.create(URL);
		Response res = client.path("/monitorService/monitor/TEST")
				.accept(TYPE_XML).type(TYPE_XML).delete();
		
		System.out.println(res.getStatus());
	}
	
	/**
	 * 查询所有监控点
	 */
	@Test
	public void testGetAll() {
		WebClient client = WebClient.create(URL);
		MonitorDTO res = client.path("/monitorService/monitors")
				.accept(TYPE_XML).type(TYPE_XML).get(MonitorDTO.class);

		List<Monitor> list = res.getMonitor();
		if(list != null){
			for(Monitor monitor : list){
				System.out.println(monitor.getSystemName());
			}
		}
	}
}
