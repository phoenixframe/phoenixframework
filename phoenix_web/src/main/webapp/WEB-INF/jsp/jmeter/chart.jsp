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
    	var responseTimeData = ${responseTimeData};
    	var bytesPerSecondData = ${bytesPerSecondData};
    	var kbPerSecondData = ${kbPerSecondData};
    	var totalBytesData = ${totalBytesData};
    	var totalCountsData = ${totalCountsData};
    	var errorPercentageData = ${errorPercentageData};
    	var avgPageBytesData = ${avgPageBytesData};
        showChart('responseTimeData',responseTimeData.title,responseTimeData.subTitle,responseTimeData.ydata,responseTimeData.xdata,responseTimeData.toolTip,responseTimeData.chartDataList);
        showChart('bytesPerSecondData',bytesPerSecondData.title,bytesPerSecondData.subTitle,bytesPerSecondData.ydata,bytesPerSecondData.xdata,bytesPerSecondData.toolTip,bytesPerSecondData.chartDataList);
        showChart('kbPerSecondData',kbPerSecondData.title,kbPerSecondData.subTitle,kbPerSecondData.ydata,kbPerSecondData.xdata,kbPerSecondData.toolTip,kbPerSecondData.chartDataList);
        showChart('totalBytesData',totalBytesData.title,totalBytesData.subTitle,totalBytesData.ydata,totalBytesData.xdata,totalBytesData.toolTip,totalBytesData.chartDataList);
        showChart('totalCountsData',totalCountsData.title,totalCountsData.subTitle,totalCountsData.ydata,totalCountsData.xdata,totalCountsData.toolTip,totalCountsData.chartDataList);
        showChart('errorPercentageData',errorPercentageData.title,errorPercentageData.subTitle,errorPercentageData.ydata,errorPercentageData.xdata,errorPercentageData.toolTip,errorPercentageData.chartDataList);
        showChart('avgPageBytesData',avgPageBytesData.title,avgPageBytesData.subTitle,avgPageBytesData.ydata,avgPageBytesData.xdata,avgPageBytesData.toolTip,avgPageBytesData.chartDataList);
        showmetrics(0);
    });
    
    function showmetrics(inx){
    	var metricsDatas = ${metricsData};
    	var metricsData = metricsDatas[inx];
        showChart('metricsData',metricsData.title,metricsData.subTitle,metricsData.ydata,metricsData.xdata,metricsData.toolTip,metricsData.chartDataList);
    }
    function conv(str){
    	return JSON.parse('['+str.substring(0, str.length-1)+']')
    }
    
    function showChart(elementId,title1,title2,ytitle,xdata,toolTip,chartDataList) {
        $('#'+elementId+'').highcharts({
            chart: {
                type: 'spline'
            },
            title: {
            	useHTML:true,
                text: title1,
                x: -20 //center
            },
            subtitle: {
                text: title2,
                x: -20
            },
            xAxis: {
                categories: xdata
            },
            yAxis: {
                title: {
                    text: ytitle
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: toolTip,
                crosshairs: true,
                shared: true
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },
            series: chartDataList
        });
    }
    
    </script>
    
</head>
<body>
<form class="form-inline definewidth m20" action="index.jsp" method="get">  
       ${summery }
       <hr>
</form>
<div id="responseTimeData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="totalCountsData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="errorPercentageData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="bytesPerSecondData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="kbPerSecondData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="totalBytesData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="avgPageBytesData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
<div id="metricsData" style="min-width: 310px; height: 300px; margin: 0 auto"></div>
</body>
</html>