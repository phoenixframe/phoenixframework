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
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
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
    var ss,sf,cs,cf;
    var batchId,caseName,startTime;
    $(function(){
        if($("#batchId").val() != undefined)showChart();
    });
    
    function showChart() {
        var caseLogId = $(".chosen-select").val();
        dwrService.listCaseStatus(parseInt(caseLogId),function(statusList){
    		for(var i=0;i<statusList.length;i++){
    			if(statusList[i].type == 'STEP'){
    				ss = statusList[i].success;
    				sf = statusList[i].fail;
    				batchId = statusList[i].batchId;
    				caseName = statusList[i].casename;
    				startTime = statusList[i].startTime;
    			} else {
    				cs = statusList[i].success;
    				cf = statusList[i].fail;
    			}
    		}
            $('#container').highcharts({
                chart: {
                    type: 'column'
                },
                title: {
                    text: batchId+'/'+caseName
                },
                subtitle: {
                    text: startTime
                },
                xAxis: {
                    categories: [
                        'STEP',
                        'CHECKPOINT',
                    ],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '通过/失败个数'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    name: 'Success',
                    data: [ss,cs]

                }, {
                    name: 'Fail',
                    data: [sf,cf]

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