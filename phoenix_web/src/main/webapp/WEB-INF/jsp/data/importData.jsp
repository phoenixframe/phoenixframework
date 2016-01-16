<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
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
<sf:form method="post" action="" enctype="multipart/form-data"  modelAttribute="sheetContentDTO">
<input type="hidden" id="caseId" value="${caseBean.id }">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft"></td>
        <td>
        	<sf:input type="hidden" path="caseId" value="${caseBean.id }"></sf:input>
        	<sf:input type="hidden" path="caseName" value="${caseBean.caseName }"></sf:input>
			为用例：[ ${caseBean.caseName }${casename } ]批量导入数据，支持xls和xlsx。导入前请确认数据文件格式，否则会导入失败。
        </td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">选择文件</td>
        <td colspan="2">
        	<input type="file" name="attachs"/><c:if test="${not empty errorInfo }"><span style="color:red">${errorInfo }</span></c:if>
        </td>
    </tr>
    <tr>
    	<td>输入Sheet页名称</td>
    	<td><sf:input type="text" path="sheetName"></sf:input><sf:errors path="sheetName"  style="color:red"></sf:errors></td>
    	<td><sf:checkbox path="isRewrite" checked="checked"/> 是否强制覆盖已存在的数据</td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
        <td></td>
    </tr>
</table>
</sf:form>
<script type="text/javascript">
    $(function () {       
		$('#backid').click(function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/data/INTERFACE_CASE/list/"+${caseBean.id }${caseid};
		 });

    });
</script>
</body>
</html>
