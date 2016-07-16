<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>附件列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/TableSort.js"></script>
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
<form class="form-inline definewidth m20" action="select" method="post">  
    附件名称：
    <input type="text" name="keyWord" id="keyWord" class="abc input-default" placeholder="支持模糊查询..." value="${keyWord }"/>&nbsp;&nbsp;  
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" onClick="$.tools.add('<%=request.getContextPath()%>/attach/add');">添加附件</button>
</form>
<table id="tblist" width="98%" class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr role="head">
        <th sort="true"><button type="button" class="btn btn-default btn-sm">
          <span class="glyphicon glyphicon-sort"></span> Sort
        </button></th>
        <th>附件名称</th>
        <th>附件类型</th>
        <th>附件地址</th>
        <th>创建时间</th>
        <th width="5%">管理操作</th>
    </tr>
    </thead>
    <tbody>
       <c:forEach items="${datas.datas}" var="cs">
	     <tr>
            <td>${cs.id }</td>
            <td>${cs.attachName }
            <td>${cs.attachType }</td>
            <td><a href="${cs.attachPath }" target="_blank" title="点击查看附件">${cs.attachPath }</a></td>
            <td><fmt:formatDate value="${cs.createDate }" pattern="yyyy-MM-dd HH:mm:ss" ></fmt:formatDate></td>
            <td>
                  <a href="javascript:$.alerts.delconfirm('delete/${cs.id}');"><span class="label label-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除附件</span></a>
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