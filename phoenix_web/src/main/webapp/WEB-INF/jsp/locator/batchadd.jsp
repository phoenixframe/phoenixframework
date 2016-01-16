<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>定位信息批量新增</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/xheditor/xheditor-1.2.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/xheditor/xheditor_lang/zh-cn.js"></script>
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

@media ( max-width : 980px) {
	/* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}

.rowOperBtn {
	font-size: 12pt;
	color: #003399;
	border: 1px #003399 solid;
	color: #006699;
	border-bottom: #93bee2 1px solid;
	border-left: #93bee2 1px solid;
	border-right: #93bee2 1px solid;
	border-top: #93bee2 1px solid;
	background-color: #e8f4ff;
	cursor: pointer;
	font-style: normal;
	width: 20px;
	height: 22px;
	font-family: Verdana;
	font-family: Georgia;
	_font-family: Tahoma;
	padding: 0 10px 1px;
	padding: 3px 3px 1px;
	_padding: 0 4px 1px;
	line-height: 18px;
	line-height: 14px;
	_line-height: 16px;
}
</style>
</head>
<form method="post" action="${caseBean.id}" id="batchAddForm">
<table class="table table-bordered table-hover definewidth m10" id="OwnershipStructure">
		<tr>
		<td colspan="3"><input name="caseId" id="caseId" type="hidden" value="${caseBean.id }"/>所属用例：${caseBean.caseName } </td>
		</tr>
				<tr>
					<td>数据标识</td>
					<td>定位数据</td>
					<td>数据类型</td>
				</tr>
			<tr id="StructureRight">
				<td><input type="text" name="locatorBeanList[0].locatorDataName"/></td>
				<td><input type="text" name="locatorBeanList[0].locatorData"/><input name="locatorBeanList[0].caseBean.id" type="hidden" value="${caseBean.id }"/></td>
				<td>          
					 <select name="locatorBeanList[0].locatorType">
					 	<c:forEach items="${types }" var="ts">
	            	 		<option value="${ts.value }">${ts.value }</option>
					 	</c:forEach>
	          		 </select>&nbsp;&nbsp;&nbsp;
	          		 <input id="btnAddRow" class="rowOperBtn" onclick="AddStructureRow()" type="button" value=" + " /> 
          		</td>
			</tr>
			<tr>
				<td colspan="3"><button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button></td>
			</tr>
</table>
</form>
<script type="text/javascript">
    $(function () {       
		$('#backid').click(function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var caseId = $("#caseId").val();
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/locator/case/"+caseId;
		 });

    });
	var index = 0;
	//表单操作 
	function AddStructureRow() {
		index++;
		var obj = document.getElementById("OwnershipStructure");
		var tr = obj.rows["StructureRight"];
		var tr = obj.insertRow(tr.rowIndex + 1);
		var trId = "trStructure" + index;
		tr.setAttribute("id", trId);
		var td0 = tr.insertCell(0);
		td0.setAttribute("align", "left");
		td0.innerHTML = "<input name='locatorBeanList["+index+"].locatorDataName' type='text'/> ";
		
		var td1 = tr.insertCell(1);
		td1.setAttribute("align", "left");
		td1.innerHTML = "<input name='locatorBeanList["+index+"].locatorData' type='text'/> <input name='locatorBeanList["+index+"].caseBean.id' type='hidden' value='${caseBean.id }'/>";
		
		var td2 = tr.insertCell(2);
		td2.setAttribute("align", "left");
		td2.innerHTML = "<select name='locatorBeanList["+index+"].locatorType'><c:forEach items='${types }' var='ts'><option value='${ts.key }'>${ts.value }</option></c:forEach></select>&nbsp;&nbsp;&nbsp;<input id='btnDelRow' class='rowOperBtn' type='button' value=' - ' onclick='DelStructureRow("+index+")'/>";
	}
	function DelStructureRow(rowIndex) {
		//var obj = document.getElementById("OwnershipStructure");
		//obj.deleteRow(rowIndex);
		$("#trStructure"+rowIndex).remove();
	}
</script>
</body>
</html>
