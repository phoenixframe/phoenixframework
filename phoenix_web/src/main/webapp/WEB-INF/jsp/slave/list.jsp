<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>分机列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
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
<form class="form-inline definewidth m20" action="index.jsp" method="get">  
<button type="button" class="btn btn-success" onClick="$.tools.add('<%=request.getContextPath()%>/slave/add');">新增节点</button>&nbsp;&nbsp;
<a href="<%=request.getContextPath()%>/druid" target="_blank">查看本机DB连接池信息</a>
</form>
<input type="hidden" id="scenarioId" value="${scenId }">
<table id="tblist" class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr role="head">
        <th sort="true">
        <button type="button" class="btn btn-default btn-sm">
          <span class="glyphicon glyphicon-sort"></span> Sort
        </button>
        </th>
        <th width="15%">节点IP</th>
        <th width="22%">节点说明</th>
        <th width="40%">附件路径（自动产生）</th>
        <th width="18%">管理操作</th>
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
                  <a href="update/${ss.id}"><span class="label label-primary"><span class="glyphicon glyphicon-edit"></span>&nbsp;编辑节点</span></a>
                  <a href="javascript:$.alerts.delconfirm('delete/${ss.id}');"><span class="label label-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除节点</span></a>
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
     $("#tblist").sorttable();
 });
</script>    
</body>
</html>
