<%@page import="org.phoenix.basic.paging.SystemContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<pg:pager export="curPage=pageNumber" 
	items="${param.totalRecord }" 
	maxPageItems="<%=SystemContext.getPageSize() %>"
	url="${param.url }">
	<span style="float:right;padding:6px;">
	共
	<pg:last>
		${pageNumber } 页  ${param.totalRecord }条记录,每页显示<%=SystemContext.getPageSize() %>条,
	</pg:last>
	<c:forEach items="${param.params }" var="p">
		<pg:param name="${p }"/>
	</c:forEach>
	<pg:first>
		<a href="${pageUrl }" class="pager_link">首页</a>
	</pg:first>
	<pg:prev>
		<a href="${pageUrl }" class="pager_link">上一页</a>
	</pg:prev>
	<pg:pages>
		<c:if test="${curPage eq pageNumber }">
			[ ${pageNumber } ]
		</c:if>
		<c:if test="${curPage != pageNumber }">
			<a href="${pageUrl }" class="pager_link">[ ${pageNumber } ]</a>
		</c:if>
	</pg:pages>
	<pg:next>
		<a href="${pageUrl }" class="pager_link">下一页</a>
	</pg:next>
	<pg:last>
		<a href="${pageUrl }" class="pager_link">尾页</a>
	</pg:last>
		</span>
	</pg:pager>
