<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>分机列表</title>
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
<form class="form-inline definewidth m20" action="index.jsp" method="get">  
<button type="button" class="btn btn-success" id="addnew">新增节点</button>
</form>
<input type="hidden" id="scenarioId" value="${scenId }">
<table class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr>
        <th width="8%">节点编号</th>
        <th width="15%">节点IP</th>
        <th width="22%">节点说明</th>
        <th width="40%">附件路径（自动产生）</th>
        <th width="15%">管理操作</th>
    </tr>
    </thead>
    <tbody>
       <c:forEach items="${datas.datas}" var="ss">
	     <tr>
            <td>${ss.id }</td>
            <td><a href="http://${ss.slaveIP }/phoenix_node" title="详细信息">${ss.slaveIP }</a></td>
            <td>${ss.remark }</td>
            <td>${ss.attachPath }</td>
            <td>
                  <a href="update/${ss.id}">编辑节点</a>&nbsp;&nbsp;
                  <a href="delete/${ss.id}">删除节点</a>&nbsp;&nbsp;
            </td>
        </tr>
        </c:forEach>
        </tbody>
     </table>
<div class="inline pull-right page">
		<jsp:include page="/jsp/pager.jsp">
			<jsp:param value="${datas.total }" name="totalRecord"/>
			<jsp:param value="list" name="url"/>
		</jsp:include>
 </div>   
 <script>
    $(function () {
		$('#addnew').click(function(){
			window.location.href="add";
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
</body>
</html>
