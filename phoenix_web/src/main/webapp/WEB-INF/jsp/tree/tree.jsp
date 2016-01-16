<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>业务树形图</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/tree.css" />
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
    <script type="text/javascript">
		 //为节点添加展开，关闭的操作
		$(function(){
		    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', '点击收起');
		    $('.tree li.parent_li > span').on('click', function (e) {
		        var children = $(this).parent('li.parent_li').find(' > ul > li');
		        if (children.is(":visible")) {
		            children.hide('fast');
		            $(this).attr('title', '点击展开').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
		        } else {
		            children.show('fast');
		            $(this).attr('title', '点击收起').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
		        }
		        e.stopPropagation();
		    });
		});
	</script>
</head>
<body>
<div class="tree well">
  <ul>
    <li> <span><i class="icon-folder-open"></i> 业务根目录</span>
      <ul>
      	<c:forEach items="${scenarios }" var="ss">
	        <li> <span><i class="icon-minus-sign"></i> ${ss.scenarioName } <a href="<%=request.getContextPath()%>/scenario/update/${ss.id}">编辑</a><c:forEach items="${scenariotasks }" var="sts"><c:if test="${sts.taskData eq ss.id }"> | <a href="<%=request.getContextPath()%>/task/update/${sts.id}">${sts.taskName }</a> [ ${sts.taskType } ] 
	            <c:choose>
      			    <c:when test="${sts.taskStatusType eq 'SUCCESS' }"><font style="color:#66CD00">[ ${sts.taskStatusType } ]</font></c:when>
      				<c:when test="${sts.taskStatusType eq 'FAIL' }"><font style="color:red">[ ${sts.taskStatusType } ]</font></c:when>
      				<c:when test="${sts.taskStatusType eq 'RUNNING' }"><font style="color:blue">[ ${sts.taskStatusType } ]</font></c:when>
      				<c:otherwise>[ ${sts.taskStatusType } ]</c:otherwise>
     			</c:choose>
	        </c:if></c:forEach></span>
	          <ul>
		         <c:forEach items="${ss.caseBeans }" var="cs">
		            <li> <span><i class="icon-leaf"></i> ${cs.caseName } <a href="<%=request.getContextPath()%>/case/update/${cs.id}">编辑</a> 
		              	<c:choose>
		              	<c:when test="${cs.caseType eq 'WEB_CASE' }">
		              		<c:forEach items="${casetasks }" var="cts">
		              			<c:if test="${cts.taskData eq cs.id }"> | <a href="<%=request.getContextPath()%>/task/update/${cts.id}">${cts.taskName }</a> [ ${cts.taskType } ] 
		              			<c:choose>
		              			    <c:when test="${cts.taskStatusType eq 'SUCCESS' }"><font style="color:#66CD00">[ ${cts.taskStatusType } ]</font></c:when>
		              				<c:when test="${cts.taskStatusType eq 'FAIL' }"><font style="color:red">[ ${cts.taskStatusType } ]</font></c:when>
		              				<c:when test="${cts.taskStatusType eq 'RUNNING' }"><font style="color:blue">[ ${cts.taskStatusType } ]</font></c:when>
		              				<c:otherwise>[ ${cts.taskStatusType } ]</c:otherwise>
		              			</c:choose>
		              			</c:if>
		              		</c:forEach>
		              	</c:when>
		              	<c:when test="${cs.caseType eq 'INTERFACE_CASE' }">
		              		<c:forEach items="${interfacetasks }" var="its">
		              			<c:if test="${its.taskData eq cs.id }"> | <a href="<%=request.getContextPath()%>/task/update/${its.id}">${its.taskName }</a> [ ${its.taskType } ] 
		              				<c:choose>
			              			    <c:when test="${its.taskStatusType eq 'SUCCESS' }"><font style="color:#66CD00">[ ${its.taskStatusType } ]</font></c:when>
			              				<c:when test="${its.taskStatusType eq 'FAIL' }"><font style="color:red">[ ${its.taskStatusType } ]</font></c:when>
			              				<c:when test="${its.taskStatusType eq 'RUNNING' }"><font style="color:blue">[ ${its.taskStatusType } ]</font></c:when>
			              				<c:otherwise>[ ${its.taskStatusType } ]</c:otherwise>
		              				</c:choose>
		              			</c:if>
		              		</c:forEach>
		              	</c:when>
		              	<c:when test="${cs.caseType eq 'MOBILE_CASE' }">
		              		<c:forEach items="${mobiletasks }" var="mts">
		              			<c:if test="${mts.taskData eq cs.id }"> | <a href="<%=request.getContextPath()%>/task/update/${mts.id}">${mts.taskName }</a> [ ${mts.taskType } ] 
		              				<c:choose>
			              			    <c:when test="${mts.taskStatusType eq 'SUCCESS' }"><font style="color:#66CD00">[ ${mts.taskStatusType } ]</font></c:when>
			              				<c:when test="${mts.taskStatusType eq 'FAIL' }"><font style="color:red">[ ${mts.taskStatusType } ]</font></c:when>
			              				<c:when test="${mts.taskStatusType eq 'RUNNING' }"><font style="color:blue">[ ${mts.taskStatusType } ]</font></c:when>
			              				<c:otherwise>[ ${mts.taskStatusType } ]</c:otherwise>
		              				</c:choose>
		              			</c:if>
		              		</c:forEach>
		              	</c:when>
		              	<c:otherwise></c:otherwise>
		                </c:choose>
		                </span>
		            </li>
		         </c:forEach>
	          </ul>
	        </li>
        </c:forEach>
      </ul>
    </li>
  </ul>
</div>
</body>
</html>
