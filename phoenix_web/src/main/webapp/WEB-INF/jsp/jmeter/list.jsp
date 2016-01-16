<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>性能测试用例列表</title>
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
    <script type="text/javascript">
    var myDialog;
      function start(id){
		myDialog = 	art.dialog({
			icon : 'face-smile',
			title : '提示',
			drag : true,
			resize : false,
			content : '任务发布成功，等待分机响应....',
			ok : true,
		});
		var otext = document.getElementById("controlValue"+id).innerText;
		if(otext == "启动"){
			runAjax(id,'start','停止');
		} else if(otext == "停止"){
			runAjax(id,'stop','启动');
		}
      }
      
     function runAjax(id,opera,itext){
     	var obj = null;
 		var localObj = window.location;
		var contextPath = localObj.pathname.split("/")[1];
		var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
   		 JSer.url(basePath+"/perf/"+opera+"/"+id).ajax({
			    method:"POST", 
			    success:function(d){
			    	obj = JSON.parse(d);//字符串转为json对象
			    	if(obj.result == 2){
			    		document.getElementById("controlValue"+id).innerText = itext;
			    	}
			    	myDialog.content(obj.msg);
			    }
			});
     }
   </script>
</head>
<body>
<form class="form-inline definewidth m20" action="selist" method="post">  
    用例名称：
    <input type="text" name="keyWord" id="keyWord" class="abc input-default" placeholder="支持模糊查询..." value="${keyword }"/>&nbsp;&nbsp; 
    <button type="submit" class="btn btn-primary">查询</button>&nbsp;&nbsp; <button type="button" class="btn btn-success" id="addnew">新增用例</button>
</form>
	<table class="table table-hover definewidth m10">
	<c:forEach items="${datas.datas }" var="ps">
		<tr>
			<td><font size="5"><b>Id:${ps.id }</b></font><font size="5" <c:if test="${ps.status eq 'RUNNING'}"> color="blue"</c:if>><b>&nbsp;&nbsp;${ps.status }</b></font><br>sampleErrorControl:${ps.sampleErrorControl }<br>requestProtocol:${ps.requestProtocol }<br>enableThinkTime:${ps.enableThinkTime }</td>
			<td>caseName:${ps.caseName }<br>controllerLoops:${ps.controllerLoops }<br>checkPointValue:${ps.checkPointValue }<br>enableRendzvous:${ps.enableRendzvous }</td>
			<td>numThreads:${ps.numThreads }<br>connectTimeOut:${ps.connectTimeOut }<br>checkPointType:${ps.checkPointType }<br>monitoredGoals:${ps.monitedSlaves }</td>
			<td>rampTime:${ps.rampTime }<br>responseTimeOut:${ps.responseTimeOut }<br>clearCache:${ps.clearCache }<br>delayedStart:${ps.delayedStart }</td>
			<td>taskAssort:${ps.taskAssort }<br>requestMethod:${ps.requestMethod }<br>emailAttemper:${ps.emailAttemper }<br>enableProxy:${ps.enableProxy }</td>
		</tr>
		<tr>
			<td colspan="3">url:<c:choose><c:when test="${f:length(ps.fullUrl)>100 }"><a href="${ps.fullUrl }" title="${ps.fullUrl }" target='_blank'>${f:substring(ps.fullUrl ,0,100)} ...</a></c:when><c:otherwise><a href="${ps.fullUrl }" title="${ps.fullUrl }" target='_blank'>${ps.fullUrl }</a></c:otherwise></c:choose></td>
			<td colspan="2" align="center"><font size="3"><a href="<%=request.getContextPath()%>/perf/update/${ps.id}">查看</a>&nbsp;&nbsp;<a href="javascript:start('${ps.id }');"><span id="controlValue${ps.id}"><c:choose><c:when test="${ps.status eq 'RUNNING'}">停止</c:when><c:otherwise>启动</c:otherwise></c:choose></span></a>&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/perf/delete/${ps.id}">删除</a>&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/perf/monitor/${ps.id}">监控</a>&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/perf/history/${ps.id}">历史</a></font></td>
		</tr>
	  </c:forEach>
	</table>
<div class="inline pull-right page">
		<jsp:include page="/jsp/pager.jsp">
			<jsp:param value="${datas.total }" name="totalRecord"/>
			<jsp:param value="list" name="url"/>
		</jsp:include>
 </div>       
</body>
</html>
<script>
    $(function () {
		$('#addnew').click(function(){
			var localObj = window.location;
			var contextPath = localObj.pathname.split("/")[1];
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/perf/add";
		 });
    });

	function del(id)
	{
		if(confirm("确定要删除吗？"))
		{
			var url = "index.jsp";
			window.location.href=url;		
		}
	
	}
</script>