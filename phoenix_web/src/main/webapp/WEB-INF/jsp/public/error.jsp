<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>异常信息</title>
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
    <script>
 $(document).ready(function() {
    $('#show').click(function() {
      var display = $('#info').css("display");
      if(display=='none') {
       $('#info').show();
       $('#show').text("隐藏全部异常栈信息");
      } else {
       $('#info').hide();
       $('#show').text("显示全部异常栈信息");
      }
     });
   });
</script>
</head>
<body>
<form class="form-inline definewidth m20">  
    <h4>捕捉到异常：</h4>
    <hr>
    <p>异常信息：${exception.message }</p>
	<p>异常坐标：${exception.stackTrace[0] }</p>
	<p>详细内容：<span id='show' style='cursor:pointer;color:blue;text-decoration:underline;'>显示全部异常栈信息</span>&nbsp;&nbsp;&nbsp;<span style='cursor:pointer;color:blue;text-decoration:underline;' onclick="history.go(-1)">返回</span></p>
	<div id='info' style='display:none;'>
		<c:forEach items="${exception.stackTrace }" var="es" varStatus="ev">
			${exception.stackTrace[ev.count] }
		</c:forEach>
	</div>
</form>
</body>
</html>