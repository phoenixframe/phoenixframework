<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>统计图表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/chosen.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.sorted.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/dwr/engine.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/dwr/interface/dwrService.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/resources/chart/highcharts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/chart/exporting.js"></script>
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
    
    <script type="text/javascript">

    $(function(){
        if($("#batchId").val() != undefined)showChart();
    });
    
    function conv(str){
    	return JSON.parse('['+str.substring(0, str.length-1)+']')
    }
    
    function showChart() {
        var ss="";
        var sf="";
        var cs="";
        var cf="";
        var caseName="";
        var batchId,createDate,scenarioName;
        var scenarioLogId = $(".chosen-select").val();
        dwrService.listScenarioStatus(parseInt(scenarioLogId),function(statusList){
    		for(var i=0;i<statusList.length;i++){
    			if(statusList[i].type == 'STEP'){
    				ss += statusList[i].success + ",";
    				sf += statusList[i].fail + ",";
    				batchId = statusList[i].batchId;
    				caseName += "\""+statusList[i].casename + "\",";
    				createDate = statusList[i].createDate;
    				scenarioName=statusList[i].scenarioName;
    			} else {
    				cs += statusList[i].success + ",";
    				cf += statusList[i].fail + ",";
    			}
    		}

    	    $('#container').highcharts({
    	        chart: {
    	            type: 'spline'
    	        },
    	        title: {
    	            text: batchId+"/"+scenarioName
    	        },
    	        subtitle: {
    	            text: new Date(createDate).toLocaleString()
    	        },
    	        xAxis: {
    	            categories: conv(caseName)
    	        },
    	        yAxis: {
    	            title: {
    	                text: "步骤/检查点通过失败次数统计"
    	            },
    	            plotLines: [{
    	            	value: 0,
    	            	width: 1,
    	            	color: '#808080'
    	            }]
    	        },
    	        tooltip: {
    	        	shared: true,
    	        	crosshairs: true
    	        },
    	        plotOptions: {
    	            line: {
    	                dataLabels: {
    	                    enabled: true
    	                },
    	                enableMouseTracking: false
    	            }
    	        },
    	        series: [
    	        {
    	            name: 'STEP_SUCCESS',
    	            data: conv(ss)
    	        }, 
    	        {
    	            name: 'STEP_FAIL',
    	            data: conv(sf)
    	        }, 
    	        {
    	            name: 'CHECKPOINT_SUCCESS',
    	            data: conv(cs)
    	        }, 
    	        {
    	            name: 'CHECKPOINT_FAIL',
    	            data: conv(cf)
    	        }]
    	    });
    	});
    }
    
    </script>
    
</head>
<body>
<form class="form-inline definewidth m20" action="index.jsp" method="get">  
       批次值：
    <select data-placeholder="请选择一个批次" id="chosen-select" class="chosen-select" style="width:310px;" tabindex="2">
      	<option value=""></option>
      	<c:forEach items="${blist }" var="bl">
      	   <c:choose>
      	      <c:when test="${not empty lbean }">
      	      	<c:choose>
      	   		   <c:when test="${lbean.id eq bl.id }">
      	   		   		<option selected="selected" value="${bl.id }">${bl.id } - ${bl.batchId } ${bl.taskType }</option>
      	   		   </c:when>
      	   		   <c:otherwise>
      	   		  	 	<option value="${bl.id }">${bl.id } - ${bl.batchId } ${bl.taskType }</option>
      	   		   </c:otherwise>
      	   		</c:choose>
      	      </c:when>
      	      <c:otherwise>
      	      	<option value="${bl.id }">${bl.id } - ${bl.batchId } ${bl.taskType }</option>
      	      </c:otherwise>
      	   </c:choose>
			
      	</c:forEach>
     </select>
         &nbsp;&nbsp;  
    <button type="button" onclick="javascript:showChart()" class="btn btn-primary">查询</button>&nbsp;&nbsp;
</form>
<input type="hidden" value="${lbean.id }" id="batchId"/>
<div id="container" style="min-width: 310px; height: 500px; margin: 0 auto"></div>
  <script type="text/javascript">
  	$(".chosen-select").chosen();
  </script>
</body>
</html>