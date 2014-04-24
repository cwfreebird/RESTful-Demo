package com.csair.ued.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.csair.ued.dao.MonitorDao;
import com.csair.ued.dto.Monitor;
import com.csair.ued.dto.MonitorDTO;
/**
 * 监控服务接口
 * @author caiwei
 *
 */
@Path("/monitorService")
@Produces({"application/json","application/xml"})
public class MonitorService {
	/**
	 * 新增监控点(单个)
	 * @param monitor
	 * @return
	 */
	@POST
	@Path("/monitor")
	@Consumes({"application/json","application/xml"})
	public Response addMonitor(Monitor monitor) {
		System.out.println("*********addMonitor called*********");
		
		if (monitor == null) {  
			return Response.status(Status.BAD_REQUEST).build();  
		} else {
			MonitorDao.getInstance().addMonitor(monitor);
			return Response.ok(monitor).build();  
		}  

	}
	/**
	 * 新增监控点(批量)
	 * @param dto
	 * @return
	 */
	@POST
	@Path("/monitors")
	@Consumes({"application/json","application/xml"})
	public Response addMonitorList(MonitorDTO dto) {
		System.out.println("*********addMonitorList called*********");
		
		if (dto == null || dto.getMonitor().size() == 0) {  
			return Response.status(Status.BAD_REQUEST).build();  
		} else {
			for(Monitor monitor : dto.getMonitor()){
				MonitorDao.getInstance().addMonitor(monitor);
			}
			return Response.ok(dto).build();  
		}  

	}
	
	/**
	 * 删除监控点
	 * @param systemName
	 * @return
	 */
	@DELETE
	@Path("/monitor/{systemName}")
	@Consumes({"application/json","application/xml"})
	public Response deleteMonitor(@PathParam("systemName") String systemName) {
		System.out.println("*********deleteMonitor called*********");
		
		Monitor monitor = MonitorDao.getInstance().getMonitor(systemName);
		if (monitor == null) {  
			return Response.status(Status.BAD_REQUEST).build();  
		} else {  
			MonitorDao.getInstance().deleteMonitor(monitor);
			return Response.ok(monitor).build();  
		}  

	}
	
	/**
	 * 根据系统名称查询监控点
	 * @param systemName
	 * @return
	 */
	@GET
	@Path("/monitor/{systemName}")
	@Produces({"application/json","application/xml"})
	public Response getMonitor(@PathParam("systemName") String systemName) {
		System.out.println("*********getMonitor called*********");
		
		Monitor monitor = MonitorDao.getInstance().getMonitor(systemName);
		if (monitor == null) {
			ResponseBuilder builder = Response.ok();
			builder.type("application/xml");
			builder.entity("<error>Monitor Not Found</error>");
			//throw new WebApplicationException(builder.build());
			//return Response.status(Status.BAD_REQUEST).build();  
			return builder.build();
		} else {  
			return Response.ok(monitor).build();  
		}  

	}
	/**
	 * 查询所有监控点
	 * @return
	 */
	@GET
	@Path("/monitors")
	@Produces({"application/json","application/xml"})
	public Response getAllMonitor() {
		System.out.println("*********getAllMonitor called*********");
		
		List<Monitor> list = MonitorDao.getInstance().getAll();
		MonitorDTO dto = new MonitorDTO();
		dto.setMonitor(list);
		return Response.ok(dto).build();  

	}
}
