<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>场景日志列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/TableSort.js"></script>
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
    场景日志详细：
    <hr>
</form>
<table id="tblist" class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr role="head">
        <th sort="true">
        <button type="button" class="btn btn-default btn-sm">
          <span class="glyphicon glyphicon-sort"></span> Sort
        </button>
        </th>
        <th>批次Id</th>
        <th>场景名称</th>
        <th>状态</th>
        <th>消息</th>
        <th>管理操作</th>
    </tr>
    </thead>
    <tbody>
       <c:forEach items="${datas.datas}" var="ss">
	     <tr>
            <td>${ss.id }</td>
            <td>${ss.batchLogBean.id }
            <td>${ss.scenarioName }</td>
            <td>${ss.taskStatusType }</td>
            <td>${ss.message }</td>
            <%-- <td><fmt:formatDate value="${cs.createDate }" pattern="yyyy-MM-dd HH:mm:ss" ></fmt:formatDate></td> --%>
            <td>
                  <a href="<%=request.getContextPath()%>/log/scenCaseList/${ss.id}"><span class="label label-primary"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;详细信息</span></a>
                  <a href="javascript:$.alerts.delconfirm('<%=request.getContextPath()%>/log/deletebatch/${batchLogId}');"><span class="label label-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除日志</span></a>
            </td>
        </tr>
        </c:forEach>
        </tbody>
     </table>
<div class="inline pull-right page">
		<jsp:include page="/jsp/pager.jsp">
			<jsp:param value="${datas.total }" name="totalRecord"/>
			<jsp:param value="${ss.id }" name="url"/>
		</jsp:include>
 </div>    
 <script>
 $(function () {
     $("#tblist").sorttable();
 });
</script>    
</body>
</html>
