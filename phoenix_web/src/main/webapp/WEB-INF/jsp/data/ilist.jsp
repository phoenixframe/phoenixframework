<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用例数据批次列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/JSer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/artDialog/artDialog.js?skin=default"></script>

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
	var localObj = window.location;
	var contextPath = localObj.pathname.split("/")[1];
	var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
    $(function () {
		var caseId = $("#caseId").val();
		$('#addnew').click(function(){
			window.location.href=basePath+"/data/INTERFACE_CASE/add/"+caseId;
		 });
		$('#importData').click(function(){
			window.location.href=basePath+"/data/import/"+caseId;
		 });
		$('#exportData').click(function(){
			if(confirm("确定导出吗？")){
				start(basePath+"/data/export/"+caseId);	
			}
		 });
    });
    
    function start(url){
    	var fileName;
    	var myDialog = 	art.dialog({
    		icon : 'face-smile',
    		title : '提示',
    		drag : true,
    		resize : false,
    		content : '正在处理请求....',
    		ok : function(){
     	   		 JSer.url(basePath+"/data/dfile/"+fileName).ajax({
    	    		    method:"POST", 
    	    	});
    		},
    	});
   		 JSer.url(url).ajax({
   		    method:"POST", 
   		    success:function(d){
   		    	var ajaxData = JSON.parse(d);//字符串转为json对象
   		    	fileName = ajaxData.obj;
   		    	myDialog.content(ajaxData.msg);
   		    },
   		});
      }
</script>
</head>
<body>
<form class="form-inline definewidth m20" action="index.jsp" method="get">  
用例名称：${caseName }&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">添加数据批次</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="importData">导入数据批次</button>&nbsp;&nbsp; 
<button type="button" class="btn btn-success" id="exportData">导出当前用例所有数据</button>
</form>
<input id="caseId" value="${caseId }" type="hidden">
<table class="table table-bordered table-hover definewidth m10" >
    <tbody>
       <c:forEach items="${datas}" var="ils" varStatus="status">
		<tr>
			<th class="tableleft" colspan="3">Expect：${ils.expectData} &nbsp;&nbsp;&nbsp;
				<a href="<%=request.getContextPath()%>/data/INTERFACE_CASE/dbatch/${caseId }/${ils.id}">删除批次</a>&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/data/INTERFACE_CASE/update/${caseId }/${ils.id}">更新期望值</a>&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/data/INTERFACE_CASE/iblist/${ils.id}">批次参数维护</a>&nbsp;&nbsp;
				数据批次： ${dataCount } / ${status.count } &nbsp;&nbsp;功能说明：${ils.remark }
			</th>
		</tr>
		<tr>
			<td>id</td>
			<td>参数名</td>
			<td>参数值</td>
		</tr>
			<c:forEach items="${ils.interfaceDatas }" var="iids">
				<tr>
					<td>${iids.id }</td>
		            <td>${iids.dataName }</td>
		            <td>${iids.dataContent }</td>
		        </tr>
	        </c:forEach>
        </c:forEach>
        </tbody>
     </table>     
</body>
</html>
