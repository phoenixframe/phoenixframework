<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用例列表</title>
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
<form class="form-inline definewidth m20" action="select" method="get">  
    用例名称：
    <input type="text" name="keyWord" id="keyWord" class="abc input-default" placeholder="支持模糊查询..." value="${keyWord }"/>&nbsp;&nbsp; 
    场景名称： 
    <input type="text" name="keyWord2" id="keyWord2" class="abc input-default" placeholder="支持模糊查询..." value="${keyWord2 }"/>&nbsp;&nbsp; 
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">新增用例</button>
</form>
<input type="hidden" id="scenarioId" value="${scenId }">
<table  id="tblist" class="table table-bordered table-hover definewidth m10" >
    <thead>
    <tr role="head">
        <th width="3%" sort="true">
        <button type="button" class="btn btn-default btn-sm">
          <span class="glyphicon glyphicon-sort"></span> Sort
        </button>
        </th>
        <th>场景名称</th>
        <th>用例名称</th>
        <th>关联类名</th>
        <th>状态</th>
        <th>用例类型</th>
        <th>消息类型</th>
        <th>是否删除</th>
        <th>功能说明</th>
        <th>创建时间</th>
        <th width="24%">管理操作</th>
    </tr>
    </thead>
    <tbody>
       <c:forEach items="${datas.datas}" var="cs">
	     <tr>
            <td>${cs.id }</td>
            <td><a href="<%=request.getContextPath()%>/scenario/select?keyWord=${cs.scenarioBean.scenarioName }">${cs.scenarioBean.scenarioName }</a>
            <td>${cs.caseName }</td>
            <td>${cs.className }</td>
            <c:choose>
	            <c:when test="${cs.status eq 1}">
	               <td>启用</td>
	              </c:when>
	               <c:otherwise>
	               <td>禁用</td>
	               </c:otherwise>
            </c:choose>
            <td>
            	${cs.caseType }
            </td>
            <td>
            	${cs.msgSendType }
            </td>
            <td>
            	${cs.deleteMsg }
            </td>
            <td>${cs.remark }</td>
            <td><fmt:formatDate value="${cs.createDate }" pattern="yyyy-MM-dd HH:mm:ss" ></fmt:formatDate></td>
            <td>
                  <a href="update/${cs.id}"><span class="label label-primary"><span class="glyphicon glyphicon-edit"></span>&nbsp;编辑</span></a>
                  <c:if test="${cs.caseType eq 'WEB_CASE' || cs.caseType eq 'MOBILE_CASE'}"><a href="<%=request.getContextPath()%>/locator/case/${cs.id}"><span class="label label-success"><span class="glyphicon glyphicon-map-marker"></span>&nbsp;定位</span></a></c:if>
                  <a href="<%=request.getContextPath()%>/data/INTERFACE_CASE/list/${cs.id}"><span class="label label-warning"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;数据</span></a>
                  <a href="javascript:$.alerts.delconfirm('<%=request.getContextPath()%>/case/delete/${cs.id}');"><span class="label label-danger"><span class="glyphicon glyphicon-remove"></span>&nbsp;删除</span></a>
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
    $("#tblist").sorttable();
</script>