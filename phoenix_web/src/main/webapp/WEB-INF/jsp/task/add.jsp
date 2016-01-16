<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>增加任务</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/chosen.jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrService.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/typechoose.js"></script>
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }
        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }
    </style>
    
    <script type="text/javascript">
    	function getSelect(data){
    		$("#taskData").empty();
    		typeSelect(data);
            $(".chosen-select").chosen();
    	}
    	function constNum(num){
    		if(num == "1"){
    			$("#taskParameter").val("0/5 * * * * ?");
    		}else if(num == "2"){
    			$("#taskParameter").val("0 0/3 * * * ?");
    		}else if (num == "3"){
    			$("#taskParameter").val("0 0 */1 * * ?");
    		} else if (num == "4"){
    			$("#taskParameter").val("0 0 10 * * ?");
    		}
    	}
    </script>
</head>
<sf:form method="post" action="add" modelAttribute="taskModelDTO" id="addForm">
<table class="table table-bordered table-hover definewidth m10">
    <tr>
        <td width="10%" class="tableleft">任务名称</td>
        <td colspan="2"><sf:input path="taskName" style="width:255px;" /><sf:errors path="taskName"/></td>
    </tr>
    <tr>
        <td class="tableleft">任务类型</td>   
        <td colspan="2">
        <%-- <sf:radiobuttons path="taskType" items="${types }"/> --%>
        <sf:select path="taskType" style="width:260px;" onchange="getSelect(this.value)">
           <option value="">请选择一个类型</option>
           <sf:options items="${types }"/>
        </sf:select>
        </td>
    </tr>
    <tr>
        <td class="tableleft">任务数据</td>  
        <td colspan="2"><%-- <sf:input path="taskData"/><sf:errors path="taskData"/> --%>
        	<sf:select data-placeholder="请选择一个任务" class="chosen-select" style="width:260px;" tabindex="2" path="taskData">

	        </sf:select>
	        <sf:errors path="taskData"/>
        </td>
    </tr>  
    <tr>
        <td class="tableleft">选择执行机</td>   
        <td colspan="2">
           <sf:select path="slaveId" style="width:260px;" >
             <c:forEach items="${slaves}" var="sl">
              <option value="${sl.id }">${sl.slaveIP } -- ${sl.remark }</option>
              </c:forEach>
           </sf:select>
              &nbsp;&nbsp;若列表为空，代表暂无可用执行机
        </td>
    </tr>  
    
  	<tr>
		<td class="tableleft" rowspan="2">执行参数</td>
		<td rowspan="2"><sf:input path="taskParameter" id="taskParameter" style="width:255px;" /></td>
	</tr>
	<tr>
		<td>
			若不输入，则默认仅执行一次或仅需要手动启动<br>
			常用参数：<a href="javascript:constNum('1')">每5秒一次</a>&nbsp;&nbsp;<a href="javascript:constNum('2')">每3分钟一次</a>
			&nbsp;&nbsp;<a href="javascript:constNum('3')">每1小时一次</a>&nbsp;&nbsp;<a href="javascript:constNum('4')">每天10点</a>
			&nbsp;&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/task/strategyList">更多...</a>
		</td>
	</tr>
    
    <tr>
        <td class="tableleft"></td>
        <td colspan="2">
            <button type="submit" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-success" name="backid" id="backid">返回列表</button>
        </td>
    </tr>
</table>
</sf:form>
    <script type="text/javascript">
  		$(".chosen-select").chosen();
  	</script>
<script type="text/javascript">
    $(function () {       
		$('#backid').click(function(){
				window.location.href="list";
		 });
    });
</script>
</body>
</html>
