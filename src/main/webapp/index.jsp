<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=contextPath %>/scripts/jquery-1.8.3.js" type="text/javascript"></script>
<title>商研统一监控平台UED</title>
<script type="text/javascript">
//监控状态窗口
var refreshMonitor;
//当前时间窗口
var timer;
//声音开关
var readyForAlarm ;
//报警重复
var alerm = false;
//是否有异常
var isRed = false;

$(document).ready(function(){
	readyAlarm();
	//页面刷新频率
	refreshMonitorStatus();
	timer = setInterval(monitoringTime, 1000);
});

function refreshMonitorStatus(){
	var imgPre = "<%=contextPath%>/images/";
	var imgSuf = "_big.png";
	$.ajax({
			type: 'post',
			cache: false,
			url:'<%=contextPath%>/monitorServlet',
			success: function (result){
				try {
					isRed = false;//清除异常
					var re = $.parseJSON(result);
					var monitorDiv = "";
					
					$.each(re.data, function(index, item){
						var systemName = item.systemName;
						var status = item.status;
						var url = item.url;
						var mp3 = item.mp3;
						var refreshTimestamp = item.refreshTimestamp;
						
						var imgPath =  imgPre + status + imgSuf;
						
						monitorDiv += "<li>" + systemName + "</li>";
						
						//复合判断，用于有异常时，只加载一次声音
						if(isRed == false && status == "R"){
							isRed = true;
							if(alerm == false){//控制重复播放，如果已经在报警，不重复播放
								playAlarm();
							}
						}
					});
					
					if(isRed == false){
						$("#alarm").attr("src","");//无异常，清除报警
						alerm = false;
					}
					
					monitorDiv = "<ul>" + monitorDiv + "</ul>";
					$("#monitorDiv").html(monitorDiv);
				} catch(e){
					alert("首页监控异常:" + e);
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown){
	            clearTimeout(refreshMonitor);
	            clearInterval(timer);
	            $("#timer").html("监控发生异常...请与管理员联系");
	        }
		});
		
	//页面刷新频率
	var frequency = $.trim($("#frequency").val());
	/*
	if(!/^[0-9]+/.test(frequency)){
		alert("刷新频率只能为大于1的整数");
		frequency = "60";
		$("#frequency").val(60);
	}
	*/
	refreshMonitor = setTimeout(refreshMonitorStatus , parseInt(frequency) * 1000);
}
//报警
function playAlarm(){
	try{
		if(isRed == true && readyForAlarm == true){
			$("#alarm").attr("src","<%=contextPath %>/sound/alarm.mp3");
			alerm = true;
		}
	}
	catch(e){
		alert("播放报警声音文件出错,声音文件未载入,请刷新页面载入."+e);
	}
}
//声音开关
function alermOnOff(){
	if(readyForAlarm){
		pauseAlarm();
	} else {
		readyAlarm();
	}
}
//关闭声音
function pauseAlarm(){
	$("#alarm").attr("src","");
	readyForAlarm = false;
	alerm = false;
	$('#alarmText').html('声音:关闭');
}
//打开声音
function readyAlarm(){
	readyForAlarm = true;
	alerm = false;
	$('#alarmText').html('声音:开启');
}
//显示当前监控时间
function monitoringTime(){
	$("#timer").html(getClock());
}
function getClock() {
	var hours, minutes, seconds;
	var intHours, intMinutes, intSeconds;
	var today;
	today = new Date();
	intHours = today.getHours();
	intMinutes = today.getMinutes();
	intSeconds = today.getSeconds();
	
	hours = formatTime(intHours);
	minutes = formatTime(intMinutes);
	seconds = formatTime(intSeconds);
	
	var timeString = "正常监控中...当前时间:" + hours + ":" + minutes + ":" + seconds;
	return timeString;
}

function formatTime(value) {
	var val = "";
	if (value == 0) {
		val = "00";
	} else if (value < 10) { 
		val = "0" + value;
	} else {
		val = value;
	}
	
	return val;
}
</script>
</head>

<body>
	<%--刷新频率(秒): --%><input type="hidden" value="10" id="frequency" />
	<bgsound id="alarm" name="alarm" loop=-1 src=""/>
	<div class="service-index">
	   	<h2>
	       	<ul>
				<li><a href="#" onclick="alermOnOff();"><div id="alarmText">声音:开启</div></a>&nbsp;</li>
				<li><div id="timer" >启动监控...</div></li>
			</ul>
		</h2>
	</div>
	<div id="monitorDiv"></div>
</body>
</html>
