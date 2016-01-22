<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		.table1 { width:800px;height:60px;}
		#s2{
		 width:100px;
		 height:30px;
		 float: left;
		}
		#s3{
		 width:700px;
		 height:30px;
		 float: left;
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
    $(document).ready(function() {
    	isShow('taskAssort');
    	isShow('emailAttemper');
    	isShow('enableThinkTime');
    	isShow('enableRendzvous');
    	isShow('enableProxy');
    	isShow('enableDataSet');
    	isShow('useBodyString');
    });
      function isShow(val){
    	  if($("#"+val+"").is(":checked")){
    		  $("#is"+val+"Div").show();
    	  } else {
    		  $("#is"+val+"Div").hide();
    	  }
    }
    
    </script>

</head>
<sf:form method="post" action="" modelAttribute="performanceDTO">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="15%" class="tableleft">用例名称：</td>
        <td><sf:input path="caseName" value="${perfBean.caseName}"/><sf:errors path="caseName"/></td>
    </tr>
    <tr>
        <td class="tableleft">用例类型：</td>
        <td>JMETER_HTTP_CASE</td>
    </tr>  
    <tr>
        <td class="tableleft">被监控机器IP（;分割）：</td>
        <td><sf:input path="monitedSlaves" value="${perfBean.monitedSlaves}"/>&nbsp;&nbsp;请在填写的目标IP机器上先启动serverAgent，并且端口是4444</td>
    </tr> 
    <tr>
        <td class="tableleft">被测地址：</td>
        <td><sf:input path="domainURL" value="${perfBean.fullUrl}"/><sf:errors path="domainURL"/>&nbsp;&nbsp;须填写以http或https开头的url地址</td>
    </tr>
    <tr>
        <td class="tableleft">并发线程数：</td>
        <td><sf:input path="numThreads" value="${perfBean.numThreads}"/><sf:errors path="numThreads"/></td>
    </tr>
    <tr>
        <td class="tableleft">Header设置：</td>
        <td><textarea rows="3" cols="5" id="requestHeaders" name="requestHeaders">${perfBean.requestHeaders}</textarea>&nbsp;&nbsp;值之间使用->分割.若不需要留空即可</td>
    </tr>
    <tr>
      	<td class="tableleft">Host设置：</td>
        <td>
        <input type="checkbox" id="enableProxy" name="enableProxy" <c:if test="${perfBean.enableProxy eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表启用Host
            <br>
            <div id="isenableProxyDiv" style="display: none">
            	<div class="table1">
	           	 <div id="s2">Host地址:</div><div id="s3"><sf:input path="proxyHost" type="text" value="${perfBean.proxyHost}"/>只需填写代理机器的IP</div>
	           	 <div id="s2">端口设置:</div><div id="s3"><sf:input path="proxyPort" type="text" value="${perfBean.proxyPort}"/></div>
	           	 <div id="s2"> 登陆用户名:</div><div id="s3"><sf:input path="proxyUserName" type="text" value="${perfBean.proxyUserName}"/>如果不需要留空即可</div>
	           	 <div id="s2">登陆密码:</div><div id="s3"><sf:input path="proxyPassword" type="text" value="${perfBean.proxyPassword}"/>如果不需要留空即可</div>
        		</div>
        	</div>
        </td>
    </tr>
    <tr>
        <td class="tableleft">Body参数：</td>
        <td>
            <input type="checkbox" id="useBodyString" name="useBodyString" <c:if test="${perfBean.useBodyString eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表body参数将随url一起发送
            <br>
            <div id="isuseBodyStringDiv" style="display: none">
	            <div class="table1">
       				 <textarea rows="3" cols="5" id="bodyString" name="bodyString">${perfBean.bodyString}</textarea>
	        	</div>
        	</div>
        </td>
    </tr>
    <tr>
      	<td class="tableleft">参数文件：</td>
        <td>
        <input type="checkbox" id="enableDataSet" name="enableDataSet" <c:if test="${perfBean.enableDataSet eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表使用文件内容作为输入参数
            <br>
            <div id="isenableDataSetDiv" style="display: none">
	            <div class="table1">
		           	 <div id="s2">文件url地址:</div><div id="s3"><sf:input path="filePath" type="text" value="${perfBean.filePath}"/>&nbsp;&nbsp;请填写带有文件扩展名的url地址，支持正常的文本文件，其他仅支持csv格式</div>
		           	 <div id="s2">文件编码:</div><div id="s3"><sf:input path="fileEncoding" type="text" value="${perfBean.fileEncoding}"/></div>
		           	 <div id="s2">循环使用:</div><div id="s3"><input type="checkbox" id="recycle" name="recycle" <c:if test="${perfBean.recycle eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>/>&nbsp;&nbsp;勾选代表数据不足时，将从第一行开始循环读取</div>
	        	</div>
        	</div>
        </td>
    </tr>
    <tr>
        <td class="tableleft">递增时间（s）：</td>
        <td><sf:input path="rampTime" value="${perfBean.rampTime}"/><sf:errors path="rampTime"/>&nbsp;&nbsp;0表示一次全部加载</td>
    </tr>
    <tr>
      	<td class="tableleft">思考时间：</td>
        <td>
        <input type="checkbox" id="enableThinkTime" name="enableThinkTime" <c:if test="${perfBean.enableThinkTime eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表启用思考时间
            <br>
            <div id="isenableThinkTimeDiv" style="display: none">
	           	 思考时间设置（ms）:<sf:input path="thinkTime" type="text" value="${perfBean.thinkTime}"/><br>
        	</div>
        </td>
    </tr>
    <tr>
        <td class="tableleft">集合点：</td>
        <td>
         <input type="checkbox" id="enableRendzvous" name="enableRendzvous" <c:if test="${perfBean.enableRendzvous eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表启用集合点
            <br>
            <div id="isenableRendzvousDiv" style="display: none">
	            <div class="table1">
		           	 <div id="s2">集合点等待数:</div><div id="s3"><sf:input path="groupSize" value="${perfBean.groupSize}"/></div>
		           	 <div id="s2">超时时间设置:</div><div id="s3"><sf:input path="rendzvousTimeOut" value="${perfBean.rendzvousTimeOut}"/>ms</div>
	        	</div>
        	</div>
        </td>
    </tr>
    <tr>
        <td class="tableleft">单线程失败时策略：</td>
        <td>
        <select name="sampleErrorControl" id="sampleErrorControl">
        	<c:forEach items="${failStrategy }" var="fs">
        	<c:choose>
        		<c:when test="${perfBean.sampleErrorControl eq fs.key}">
        			<option value="${fs.key }" selected="selected">${fs.value }
        		</c:when>
        		<c:otherwise>
        			<option value="${fs.key }">${fs.value }
        		</c:otherwise>
        	</c:choose>
        	</c:forEach>
        	</select>
        </td>
    </tr>
        <tr>
        <td class="tableleft">单线程迭代次数：</td>
        <td><sf:input path="controllerLoops" value="${perfBean.controllerLoops}"/><sf:errors path="controllerLoops"/></td>
    </tr>
   <tr>
        <td class="tableleft">连接超时时间(ms)：</td>
        <td><sf:input path="connectTimeOut" value="${perfBean.connectTimeOut}"/><sf:errors path="connectTimeOut"/></td>
    </tr>
    <tr>
        <td class="tableleft">读取响应超时时间(ms)：</td>
        <td><sf:input path="responseTimeOut" value="${perfBean.responseTimeOut}"/><sf:errors path="responseTimeOut"/></td>
    </tr>
    <tr>
        <td class="tableleft">返回页面编码：</td>
        <td><sf:input path="contentEncoding" value="${perfBean.contentEncoding}"/><sf:errors path="contentEncoding"/></td>
    </tr>
    <tr>
        <td class="tableleft">地址请求方式：</td>
        <td><input type="radio" checked="checked" value="GET" name="requestMethod">GET&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="radio" <c:if test="${perfBean.requestMethod eq 'POST'}">checked="checked"</c:if> value="POST" name="requestMethod">POST</td>
    </tr>
    <tr>
        <td class="tableleft">检查对象：</td>
        <td>
        	<input type="radio" checked="checked" value="response_data" name="checkType">响应内容&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="radio" <c:if test="${perfBean.checkType eq 'response_code'}">checked="checked"</c:if> value="response_code" name="checkType">响应状态码&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="radio" <c:if test="${perfBean.checkType eq 'response_headers'}">checked="checked"</c:if> value="response_headers" name="checkType">响应头
        </td>
    </tr>
    <tr>
        <td class="tableleft">检查点类型：</td>
        <td><select name="checkPointType" id="checkPointType">
        	<c:forEach items="${checkPointTypes }" var="cts">
        		<c:choose>
        			<c:when test="${perfBean.checkPointType eq cts.key}">
        				<option value="${cts.key }" selected="selected">${cts.value }
        			</c:when>
        			<c:otherwise>
        				<option value="${cts.key }" >${cts.value }
        			</c:otherwise>
        		</c:choose>
        	</c:forEach>
        	</select>
        </td>
    </tr>
    <tr>
        <td class="tableleft">检查点值：</td>
        <td><sf:input path="checkPointValue" value="${perfBean.checkPointValue}"/><sf:errors path="checkPointValue"/></td>
    </tr>
    <tr>
        <td class="tableleft">执行机：</td>
        <td><sf:select path="slaveId">
        	<c:forEach items="${slaves }" var="ss">
        		    <c:choose>
    					<c:when test="${ss.id eq  perfBean.slaveId}">
    						<sf:option value="${ss.id }" selected="selected">${ss.slaveIP }--${ss.remark }</sf:option>
    					</c:when>
    					<c:otherwise>
    						<sf:option value="${ss.id }">${ss.slaveIP }--${ss.remark }</sf:option>
    					</c:otherwise>
    				</c:choose>
        	</c:forEach>
        </sf:select>
        </td>
    </tr>
    <tr>
        <td class="tableleft">缓存清理</td>
        <td>
        <input type="checkbox" id="clearCache" name="clearCache" <c:if test="${perfBean.clearCache eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>/>选中代表每次迭代都清理
        </td>
    </tr>
        <tr>
        <td class="tableleft">任务调度</td>
        <td>
         <input type="checkbox" id="taskAssort" name="taskAssort" <c:if test="${perfBean.taskAssort eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表启用任务调度，需要输入调度参数
            <br>
            <div id="istaskAssortDiv" style="display: none">
            	<div class="table1">
		            <div id="s2">startTime:</div><div id="s3"><sf:input path="startTime" value="${perfBean.startTime}"/></div>
		            <div id="s2">endTime:</div><div id="s3"><sf:input path="endTime" value="${perfBean.endTime}"/></div>
		            <div id="s2">duration:</div><div id="s3"><sf:input path="duration" value="${perfBean.duration}"/><sf:errors path="duration"/></div>
		            <div id="s2">delayTime:</div><div id="s3"><sf:input path="delayTime" value="${perfBean.delayTime}"/><sf:errors path="delayTime"/></div>
		            <div id="s2">delayedStart:</div><div id="s3"><sf:input path="delayedStart" value="${perfBean.delayedStart}"/><sf:errors path="delayedStart"/></div>
        		</div>
        	</div>
        </td>
    	<tr>
        <td class="tableleft">Email发送</td>
        <td>
            <input type="checkbox" id="emailAttemper" name="emailAttemper" <c:if test="${perfBean.emailAttemper eq 'on'}"> <% out.print("checked='checked'"); %> </c:if>  onclick="isShow(this.name)" />选中代表执行完成后会根据设置的参数发送邮件
            <br>
            <div id="isemailAttemperDiv" style="display: none">
            	<div class="table1">
	            	<div id="s2">successLimit:</div><div id="s3"><sf:input path="successLimit" value="${perfBean.successLimit}"/><sf:errors path="successLimit"/></div>
	            	<div id="s2">failureLimit:</div><div id="s3"><sf:input path="failureLimit" value="${perfBean.failureLimit}"/><sf:errors path="failureLimit"/></div>
	            	<div id="s2">fromAddress:</div><div id="s3"><sf:input path="fromAddress" value="${perfBean.fromAddress}"/><sf:errors path="fromAddress"/></div>
	            	<div id="s2">smtpHost:</div><div id="s3"><sf:input path="smtpHost" value="${perfBean.smtpHost}"/><sf:errors path="smtpHost"/></div>
	            	<div id="s2">sendTo:</div><div id="s3"><sf:input path="sendTo" value="${perfBean.sendTo}"/><sf:errors path="sendTo"/></div>
	            	<div id="s2">serverUsername:</div><div id="s3"><sf:input path="emailServerLoginName" value="${perfBean.emailServerLoginName}"/><sf:errors path="emailServerLoginName"/></div>
	            	<div id="s2">serverPassword:</div><div id="s3"><sf:input type="password" path="emailServerLoginPassword" value="${perfBean.emailServerLoginPassword}"/><sf:errors path="emailServerLoginPassword"/></div>
	            	<div id="s2">authType:</div><div id="s3"><sf:input path="authType" value="${perfBean.authType}"/><sf:errors path="authType"/></div>
        		</div>
        	</div>
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
			var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;
			window.location.href=basePath+"/perf/list";
		 });

    });
</script>
</body>
</html>
