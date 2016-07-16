<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Properties" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="java.io.File" %>
<%@ page import="java.lang.management.ManagementFactory" %>
<%@ page import="com.sun.management.OperatingSystemMXBean" %>
<%@ page import="org.hibernate.stat.Statistics" %>
<%@ page import="org.phoenix.node.util.HibernateTools" %>
<%@ page import="com.meterware.httpunit.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Node信息自检</title>
<style type="text/css">
body {
	font-size: 16px;
	font-family: Courier New,Microsoft YaHei;
}

</style>
</head>
<body>
<h2>Node Server Info:</h2>
<h3>健康情况自检：</h3>
<%
	WebConversation wc = new WebConversation();	
	WebRequest req = new GetMethodWebRequest("http://localhost:"+request.getServerPort()+"/phoenix_node/action.do?requestType=getStatus");
	out.println("<p>"+wc.getResponse(req).getText()+"</p>");
%>
<h3>硬盘及内存使用情况自检信息：</h3>
<%
	File[] roots = File.listRoots();
	for (int i=0;i<roots.length;i++) {
	    out.println("<p>DiskNum "+i+" : Total = " + roots[i].getTotalSpace()/1024/1024/1024+"G, Free  = " + roots[i].getFreeSpace()/1024/1024/1024+"G, Usable  = " + roots[i].getUsableSpace()/1024/1024/1024+"G</p>");
	}
    OperatingSystemMXBean osmb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    out.println("<p>TotalPhysicalMemorySize = " + osmb.getTotalPhysicalMemorySize() / 1024/1024 + "MB</p>");
    out.println("<p>FreePhysicalMemorySize = " + osmb.getFreePhysicalMemorySize() / 1024/1024 + "MB</p>");
%>
<h3>数据库连接池基本自检信息：</h3>
<%
	Statistics stat = HibernateTools.getStatistics();
	out.println("<p>connectCount = "+stat.getConnectCount()+"</p>");
	out.println("<p>SessionOpenCount = "+stat.getSessionOpenCount()+"</p>");
	out.println("<p>SessionCloseCount = "+stat.getSessionCloseCount()+"</p>");
	out.println("<p>QueryExecutionCount = "+stat.getQueryExecutionCount()+"</p>");
	out.println("<p>QueryExecutionMaxTime = "+stat.getQueryExecutionMaxTime()+"</p>");
	out.println("<p>SuccessfulTransactionCount = "+stat.getSuccessfulTransactionCount()+"</p>");
	out.println("<p>TransactionCount = "+stat.getTransactionCount()+"</p>");
	
%>
<h3>HttpServletRequest基本信息：</h3>
<%
	out.println("<p>LocalAddr =  "+request.getLocalAddr()+"</p>");
	out.println("<p>LocalName =  "+request.getLocalName()+"</p>");
	out.println("<p>LocalPort =  "+request.getLocalPort()+"</p>");
	out.println("<p>PathInfo =  "+request.getPathInfo()+"</p>");
	out.println("<p>PathTranslated =  "+request.getPathTranslated()+"</p>");
	out.println("<p>ServerName =  "+request.getServerName()+"</p>");
	out.println("<p>ServerPort =  "+request.getServerPort()+"</p>");
	out.println("<p>ServletPath =  "+request.getServletPath()+"</p>");
%>
<h3>环境基本信息：</h3>
<%
	Properties props = System.getProperties();
	Enumeration<?> en = props.propertyNames();
	while (en.hasMoreElements()) {
		String key = (String) en.nextElement();
		String property = props.getProperty(key);
		out.println("<p>"+key +" = "+ property +" <p>");
	}
%>
</body>
</html>