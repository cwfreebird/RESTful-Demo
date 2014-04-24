package com.csair.ued.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.csair.ued.dao.MonitorDao;
import com.csair.ued.dto.Monitor;
/**
 * 异步获取所有监控点servlet
 * @author caiwei
 *
 */
public class MonitorServlet extends HttpServlet {
	
	protected void service(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		
		List<Monitor> list = MonitorDao.getInstance().getAll();
		String monitor = writeValueAsString(list);
		monitor = "{\"data\":" + monitor + "}";
		out.write(monitor);
	}
	
	/**
	 * 转换为json字符串
	 * @param o
	 * @return
	 */
	private String writeValueAsString(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		try {
			result = mapper.writeValueAsString(o);
		} catch (IOException e) {
			System.out.println("[writeValueAsString] 转换为Json对象出错" + e);
		} finally {
			//System.out.println("[writeValueAsString] result:" + result);
		}

		return result;
	}
}
