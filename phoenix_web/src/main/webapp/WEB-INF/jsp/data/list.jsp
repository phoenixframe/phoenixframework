<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用例数据列表</title>
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
    <script>
    $(function () {
		var caseId = $("#caseId").val();
		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
		$('#batchadd').click(function(){
			window.location.href=basePath+"/data/batchadd/"+caseId;
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
</head>
<body>
<form class="form-inline definewidth m20" action="index.jsp" method="get">  
当前用例：${caseBean.caseName }  &nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" id="batchadd">新增用例数据</button>
</form>
<input type="hidden" id="caseId" value="${caseBean.id }">
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th>数据编号</th>
        <th>数据标识</th>
        <th>数据内容</th>
        <th>管理操作</th>
    </tr>
    </thead>
    <tbody>
       <c:forEach items="${datas.datas}" var="cs">
	     <tr>
            <td>${cs.id }</td>
            <td>${cs.dataName }</td>
            <td>${cs.dataContent }</td>
            <td>
                  <a href="<%=request.getContextPath()%>/data/update/${cs.caseBean.id }/${cs.id}">编辑数据</a>&nbsp;&nbsp;
                  <a href="<%=request.getContextPath()%>/data/delete/${cs.caseBean.id }/${cs.id}">删除数据</a>&nbsp;&nbsp;
            </td>
        </tr>
        </c:forEach>
        </tbody>
     </table>
<div class="inline pull-right page">
		<jsp:include page="/jsp/pager.jsp">
			<jsp:param value="${datas.total }" name="totalRecord"/>
			<jsp:param value="${caseBean.id }" name="url"/>
		</jsp:include>
 </div>       
</body>
</html>
