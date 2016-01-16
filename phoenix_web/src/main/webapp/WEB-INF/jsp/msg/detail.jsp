<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
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
<form method="post" action="">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">消息ID</td>
        <td>
        ${msgBean.id }
        </td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">任务名称</td>
        <td>${taskModel.taskName }</td>
    </tr>
    <tr>
        <td class="tableleft">消息状态</td>   
        <td>${msgBean.msgStatusType}</td>
    </tr>   
    <tr>
        <td class="tableleft">发送成功后删除</td>
        <td>
        ${msgBean.deleteMsg }
        </td>
    </tr>
    <tr>
    	<td class="tableleft">消息备注</td>
    	<td>
    	${msgBean.remark }
    	</td>
    </tr>
    <tr>
        <td class="tableleft">消息内容</td>
        <td>
            <textarea readonly="readonly" name="codeContent" style="height:300px;width:100%;">${msgBean.msgContent }</textarea>
        </td>
    </tr>
    
    <tr>
       <td class="tableleft">
       	创建时间
       </td>
       <td><fmt:formatDate value="${msgBean.createData }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="button" class="btn btn-success" name="backid" id="backid">返回消息列表</button>
        </td>
    </tr>
</table>
</form>
<script type="text/javascript">
    $(function () {       
		$('#backid').click(function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/msg/list";
		 });

    });
</script>
</body>
</html>
