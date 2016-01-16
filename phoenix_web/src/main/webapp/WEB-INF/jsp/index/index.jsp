<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>后台管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=request.getContextPath()%>/resources/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/resources/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/resources/assets/css/main-min.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/assets/js/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/assets/js/bui-min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/assets/js/common/main-min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/assets/js/config-min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/JSer.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/artDialog/artDialog.js?skin=default"></script>
	<script>
	var authMenu = null;
	var localObj = window.location;
	var contextPath = localObj.pathname.split("/")[1];
	var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
	
	    BUI.use('common/main',function(){
	    	JSer.url(basePath+"/auth/menu").ajax({
				    method:"POST", 
				    success:function(d){
				    	//字符串转为json对象
				    	authMenu = JSON.parse(d);
						new PageUtil.MainPage({
							modulesConfig : authMenu
						});
				    } 
				});

		});
	</script>
	
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

<div class="header">

    <div class="dl-title">
        <img src="<%=request.getContextPath()%>/resources/assets/img/top.png">
    </div>    
    <shiro:guest>
        <div class="dl-log"><a href="<%=request.getContextPath()%>/login" class="dl-log-quit">请登录</a></div>
    </shiro:guest>
    <shiro:user>
        <div class="dl-log" >欢迎您，<span class="dl-log-user"><shiro:principal property="nickname"/> [ <shiro:principal property="roleName"/> ]</span><a href="<%=request.getContextPath()%>/logout" title="退出系统" class="dl-log-quit">[退出]</a></div>
    </shiro:user>
</div>
<div class="content">
    <div class="dl-main-nav">
        <div class="dl-inform"><div class="dl-inform-title"></div></div>
        <ul id="J_Nav"  class="nav-list ks-clear">
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统管理</div></li>		
            <li class="nav-item dl-selected"><div class="nav-item-inner nav-order">业务管理</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">
       
    </ul>
</div>

</body>
</html>