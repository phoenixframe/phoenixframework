<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的信息编辑</title>
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
<sf:form method="post" action="${user.id}" modelAttribute="userDTO">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">用户名称</td>
        <td><sf:input path="username" value="${user.username}"/><sf:errors path="username"/></td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">用户昵称</td>
        <td><sf:input path="nickname" value="${user.nickname}"/><sf:errors path="nickname"/></td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">用户密码</td>
        <td><sf:input path="password" type="password" value="${user.password}"/>请牢记您修改后的密码且在下次登陆时使用新密码&nbsp;&nbsp;<sf:errors path="password"/></td>
    </tr>
    <tr>
       <td class="tableleft">账户类型</td>
        <td>
           <c:choose>
             <c:when test="${user.role eq 0}">
              		管理员
              </c:when>
              <c:otherwise>
              		普通账户
              </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td class="tableleft">Email</td>   
        <td><sf:input path="email" value="${user.email}"/><sf:errors path="email"/> &nbsp;多邮箱之间使用;号分割</td>
    </tr>  
    <tr>
        <td class="tableleft">创建时间</td>
        <td><fmt:formatDate value="${user.createDate }" pattern="yyyy-MM-dd HH:mm:ss" ></fmt:formatDate></td>
    </tr> 
    <tr>
        <td class="tableleft"></td>
        <td>
            <button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">取消</button>
        </td>
    </tr>
</table>
</sf:form>
<script type="text/javascript">
    $(function () {       
		$('#backid').click(function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/user/self";
		 });

    });
</script>
</body>
</html>
