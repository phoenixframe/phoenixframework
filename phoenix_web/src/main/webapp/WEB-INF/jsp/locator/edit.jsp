<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>定位信息编辑</title>
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
<sf:form method="post" action="${locatorBean.id}" modelAttribute="locatorDTO">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">所属用例</td>
        <td><sf:input path="caseId" id="caseId" type="hidden" value="${locatorBean.caseBean.id}"/>${locatorBean.caseBean.caseName }</td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">数据ID</td>
        <td><sf:input disabled="true" path="id" value="${locatorBean.id}"/></td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">数据标识</td>
        <td><sf:input path="locatorDataName" value="${locatorBean.locatorDataName}"/><sf:errors path="locatorDataName"/></td>
    </tr>
    <tr>
        <td class="tableleft">定位数据</td>   
        <td><sf:input path="locatorData" value="${locatorBean.locatorData}"/><sf:errors path="locatorData"/></td>
    </tr>   
    <tr>
       <td class="tableleft">数据类型</td>
        <td>
           <sf:select path="locatorType">
	           <c:forEach items="${types }" var="t">
	             <c:choose>
	               <c:when test="${t.key eq locatorBean.locatorType }">
	               		<sf:option value="${t.value}" selected="selected"/>
	               </c:when>
	               <c:otherwise>
	               		<sf:option value="${t.value}"/>
	               </c:otherwise>
	             </c:choose>
	           </c:forEach>
           </sf:select>
        </td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
    </tr>
</table>
</sf:form>
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
</script>
</body>
</html>
