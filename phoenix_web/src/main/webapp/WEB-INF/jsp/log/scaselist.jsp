<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用例日志列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>

    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }

    </style>
</head>
<body>
<form class="form-inline definewidth m20" action="" method="get">  
    用例日志详细：<hr>
</form>
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>日志编号</th>
        <th>场景日志Id</th>
        <th>用例名称</th>
        <th>客户端</th>
        <th>客户端IP</th>
        <th>状态</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>持续用时</th>
        <th>驱动类型</th>
        <th>管理操作</th>
    </tr>
    </thead>
    <tbody>
       <c:forEach items="${datas.datas}" var="cs">
	     <tr>
            <td>${cs.id }</td>
            <td>${cs.scenarioLogBeanId }</td>
            <td>${cs.caseName }</td>
            <td>${cs.actor }</td>
            <td>${cs.clientIP }</td>
            <td>${cs.status }</td>
            <td>${cs.startTime }</td>
            <td>${cs.endTime }</td>
            <td>${cs.duration }</td>
            <td>${cs.engineType }</td>
            <%-- <td><fmt:formatDate value="${cs.createDate }" pattern="yyyy-MM-dd HH:mm:ss" ></fmt:formatDate></td> --%>
            <td>
                  <a href="<%=request.getContextPath()%>/log/unitLogList/${cs.id}">详细信息</a>&nbsp;&nbsp;
                  <a href="<%=request.getContextPath()%>/log/deletecaselog/${cs.scenarioLogBeanId}/${cs.id }">删除日志</a>&nbsp;&nbsp;
            </td>
        </tr>
        </c:forEach>
        </tbody>
     </table>
<div class="inline pull-right page">
		<jsp:include page="/jsp/pager.jsp">
			<jsp:param value="${datas.total }" name="totalRecord"/>
			<jsp:param value="${cs.scenarioLogBeanId }" name="url"/>
		</jsp:include>
 </div>       
</body>
</html>
<script>
    $(function () {
		$('#addnew').click(function(){
			var scenarioId = $("#scenarioId").val();
			if(scenarioId == ""){
				window.location.href="add";
			}else{
				window.location.href="add/"+scenarioId;
			}
		 });
    });

	function del(id)
	{
		if(confirm("确定要删除吗？"))
		{
			var url = "index.jsp";
			window.location.href=url;		
		}
	
	}
</script>