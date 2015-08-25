package com.csair.ued.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.csair.ued.dto.Monitor;
/**
 * 所有监控点保存DAO
 * @author caiwei
 *
 */
public class MonitorDao {
	//单例
	private static MonitorDao monitorDao;
	//所有监控点map
	private static Map<String , Monitor> map = new HashMap<String , Monitor>();
	
	private MonitorDao() {
		System.out.println("====================");
	}

	public static MonitorDao getInstance() {
		if (monitorDao == null) {
			monitorDao = new MonitorDao();
		}
		
		return monitorDao;
	}
	
	/**
	 * 查询所有监控信息
	 * @return
	 */
	public List<Monitor> getAll(){
		List<Monitor> list = new ArrayList<Monitor>(map.values());
		return list;
	}
	
	/**
	 * 根据系统名称查询监控点
	 * @param systemName
	 * @return
	 */
	public Monitor getMonitor(String systemName){
		return (Monitor)map.get(systemName);
	}
	
	/**
	 * 新增监控点
	 * @param Monitor
	 */
	public void addMonitor(Monitor Monitor){
		map.put(Monitor.getSystemName(), Monitor);
	}
	
	/**
	 * 删除监控点
	 * @param Monitor
	 */
	public void deleteMonitor(Monitor Monitor){
		map.remove(Monitor.getSystemName());
	}
	
	/*static {
		Monitor Monitor = new Monitor();
		Monitor.setSystemName("VOS");
		Monitor.setMp3("MP3");
		Monitor.setRefreshTimestamp("2014-04-01 12:00:00");
		Monitor.setStatus("R");
		Monitor.setUrl("http://www.baidu.com");
		
		map.put("VOS",Monitor);
	}*/
}
