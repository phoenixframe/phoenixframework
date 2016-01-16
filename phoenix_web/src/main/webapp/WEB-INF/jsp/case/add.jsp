<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/xheditor/xheditor-1.2.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/xheditor/xheditor_lang/zh-cn.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/chosen.jquery.js"></script>
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
<sf:form method="post" action="add" modelAttribute="caseDTO" id="addForm">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">所属场景</td>
        <td>
	        <sf:select data-placeholder="请选择一个场景" class="chosen-select" style="width:210px;" tabindex="2" path="scenId">
	        	<option value=""></option>
	        	<c:forEach items="${sceanList }" var="sl">
	        	   <sf:option value="${sl.id }">${sl.scenarioName }</sf:option>
	        	</c:forEach>
	        </sf:select>
        </td>
    </tr>
    <tr>
        <td width="10%" class="tableleft">用例名称</td>
        <td><sf:input path="caseName"/><sf:errors path="caseName"/></td>
    </tr>
    <tr>
        <td class="tableleft">用例说明</td>   
        <td><sf:input path="remark"/><sf:errors path="remark"/></td>
    </tr>  
    <tr>
    	<td class="tableleft">用例类型</td>
    	<td>
    		<sf:select path="caseType">
    			<c:forEach items="${caseTypes}" var="cts">
    				<c:choose>
    					<c:when test="${f:contains(cts.value,'CASE') == true }">
    						<sf:option value="${cts.key }">${cts.value }</sf:option>
    					</c:when>
    				</c:choose>
    			</c:forEach>
    		</sf:select>
    	</td>
    </tr> 
    <tr>
        <td class="tableleft">启用状态</td>
        <td>
           <span><sf:radiobutton path="status" value="1"/>已启用</span>
           <span><sf:radiobutton path="status" value="0"/>未启用</span>
        </td>
    </tr>
    <tr>
    	<td class="tableleft">消息发送类型</td>
    	<td>
    		<sf:select path="msgSendType">
    			<sf:options items="${msgSendTypes }"/>
    		</sf:select>
    		&nbsp;&nbsp;
    		<sf:checkbox path="deleteMsg" checked="checked"/>发送成功后删除消息
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
				window.location.href="list";
		 });

    });
</script>
  <script type="text/javascript">
  $(".chosen-select").chosen();

  </script>
</body>
</html>
