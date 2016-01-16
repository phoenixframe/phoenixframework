<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>性能测试过程监控</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/Css/style.css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/ckform.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/Js/JSer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/chart/highcharts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/chart/exporting.js"></script>
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
    var refreshTime = 3000;
    var t = null;
    var t2 = null;
    var t3 = null;
    var rThreads = 0;
    var stopThreads = 0;
    var throughputs = new Array();
	
    function start(){
     	var taskId = $("#taskId").val();
    	var localObj = window.location;
    	var contextPath = localObj.pathname.split("/")[1];
    	var basePath = localObj.protocol+"//"+localObj.host+"/"+contextPath;

 		 JSer.url(basePath+"/perf/monitorstate/"+taskId).ajax({
		    method:"POST", 
		    success:function(d){
		    	var jsonObj = JSON.parse(d);//字符串转为json对象
		    	if(jsonObj.result === 0){
		    		document.getElementById("init").innerHTML="<font size=5 color='red'>"+jsonObj.msg+"</font>";
		    	} else {
			    	document.getElementById("init").innerHTML="<font size=5 color='blue'>监控中......</font>";
		    		document.getElementById("running").innerHTML = "是否正在运行："+jsonObj.obj.running;
		    		document.getElementById("startedThreads").innerHTML = "初始化完成的线程数："+jsonObj.obj.startedThreads+"&nbsp;&nbsp;|&nbsp;&nbsp;";
		    		rThreads = jsonObj.obj.activeThreads;
		    		document.getElementById("activeThreads").innerHTML = "正在执行测试任务的线程数："+rThreads+"&nbsp;&nbsp;|&nbsp;&nbsp;";
		    		stopThreads = jsonObj.obj.finishedThreads;
		    		document.getElementById("finishedThreads").innerHTML = "已经停止的线程数："+stopThreads+"&nbsp;&nbsp;|&nbsp;&nbsp;";
		    		document.getElementById("totalThreads").innerHTML = "总共设置的线程数："+jsonObj.obj.totalThreads;
		    		document.getElementById("startTime").innerHTML = "场景启动时间："+jsonObj.obj.startTime+"&nbsp;&nbsp;|&nbsp;&nbsp;";
		    		document.getElementById("endTime").innerHTML = "场景结束时间："+jsonObj.obj.endTime;
		    		document.getElementById("logContent").innerHTML = "活动状态的线程日志："+jsonObj.obj.queueString;
		    		throughputs = jsonObj.obj.resultCal.split("<br>");
		    		document.getElementById("resultCal").innerHTML = "执行过程统计："+jsonObj.obj.resultCal;
		    		document.getElementById("monitedSlaves").innerHTML = "机器资源监控：<br>"+jsonObj.obj.monitedSlaves;
		    		document.getElementById("summary").innerHTML = "测试结果统计："+jsonObj.obj.summary;
		    	}

	    		if(jsonObj.obj.running === false){
		     		JSer.url(basePath+"/perf/monitorlog/"+taskId).ajax({
		     		    method:"POST", 
		     		    success:function(data){
		    		    	document.getElementById("init").innerHTML="<font size=5 color='red'>已停止</font>";
		     		    	document.getElementById("logContent").innerHTML = data;
		     		    },
		     		});
			    	clearInterval(t);
			    	//clearInterval(t2);
			    	//clearInterval(t3);
		    	}
		    },
		}); 
  	}
    
    function showThroughputChart() {                                                  
        Highcharts.setOptions({                                                     
            global: {                                                               
                useUTC: false                                                       
            }                                                                       
        });                                                                         
                                                                                    
        var chart;                                                                  
        $('#throughput').highcharts({                                                
            chart: {                                                                
                type: 'spline',                                                     
                animation: Highcharts.svg, // don't animate in old IE               
                marginRight: 10,                                                    
                events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series0 = this.series[0];  
                        var series1 = this.series[1]; 
                        var series2 = this.series[2];
                        t3 = setInterval(function() {    
                            var max = throughputs[5]==null?0:parseInt(throughputs[5].split(":")[1]);
                            var mean = throughputs[6]==null?0:parseInt(throughputs[6].split(":")[1]);
                            var min = throughputs[7]==null?0:parseInt(throughputs[7].split(":")[1]);
                            var x = (new Date()).getTime(), // current time         
                                y = 20; //Math.random();                                  
                            series0.addPoint([x, max], true, true);  
                            series1.addPoint([x, mean], true, true); 
                            series2.addPoint([x, min], true, true);
                        }, refreshTime);                                                   
                    }
                }                                                                   
            },                                                                      
            title: {                                                                
                text: 'Throughput'                                            
            },                                                                      
            xAxis: {                                                                
                type: 'datetime',                                                   
                tickPixelInterval: 150                                              
            },                                                                      
            yAxis: {                                                                
                title: {                                                            
                    text: 'Value'                                                   
                },                                                                  
                plotLines: [{                                                       
                    value: 0,                                                       
                    width: 1,                                                       
                    color: '#808080'                                                
                }]                                                                  
            },                                                                      
            tooltip: {                                                              
                formatter: function() {                                             
                        return '<b>'+ this.series.name +'</b><br>'+                
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br>'+
                        Highcharts.numberFormat(this.y, 2);                         
                }                                                                   
            },                                                                      
            legend: {                                                               
                enabled: false                                                      
            },                                                                      
            exporting: {                                                            
                enabled: true                                                      
            },                                                                      
            series: [{                                                              
                name: 'max',                                                
                data: (function() {                                                 
                    // generate an array of random data                             
                    var data = [],                                                  
                        time = (new Date()).getTime(),                              
                        i;                                                          
                                                                                    
                    for (i = -19; i <= 0; i++) {                                    
                        data.push({                                                 
                            x: time + i * 1000,                                     
                            y: Math.random()                                        
                        });                                                         
                    }                                                               
                    return data;                                                    
                })()                                                                
            },
            {                                                              
                name: 'mean',                                                
                data: (function() {                                                 
                    // generate an array of random data                             
                    var data = [],                                                  
                        time = (new Date()).getTime(),                              
                        i;                                                          
                                                                                    
                    for (i = -19; i <= 0; i++) {                                    
                        data.push({                                                 
                            x: time + i * 1000,                                     
                            y: Math.random()                                        
                        });                                                         
                    }                                                               
                    return data;                                                    
                })()                                                                
            },
            {                                                              
                name: 'min',                                                
                data: (function() {                                                 
                    // generate an array of random data                             
                    var data = [],                                                  
                        time = (new Date()).getTime(),                              
                        i;                                                          
                                                                                    
                    for (i = -19; i <= 0; i++) {                                    
                        data.push({                                                 
                            x: time + i * 1000,                                     
                            y: Math.random()                                        
                        });                                                         
                    }                                                               
                    return data;                                                    
                })()                                                                
            }
            
            ]                                                                      
        });                                                                         
    }
    
    function showThreadChart() {                                                  
        Highcharts.setOptions({                                                     
            global: {                                                               
                useUTC: false                                                       
            }                                                                       
        });                                                                         
                                                                                    
        var chart;                                                                  
        $('#container').highcharts({                                                
            chart: {                                                                
                type: 'spline',                                                     
                animation: Highcharts.svg, // don't animate in old IE               
                marginRight: 10,                                                    
                events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series0 = this.series[0];  
                        var series1 = this.series[1]; 
                        t2 = setInterval(function() {                                    
                            var x = (new Date()).getTime(), // current time         
                                y = 20; //Math.random();                                  
                            series0.addPoint([x, rThreads], true, true);  
                            series1.addPoint([x, stopThreads], true, true); 
                        }, refreshTime);                                                   
                    }
                }                                                                   
            },                                                                      
            title: {                                                                
                text: 'Threads'                                            
            },                                                                      
            xAxis: {                                                                
                type: 'datetime',                                                   
                tickPixelInterval: 150                                              
            },                                                                      
            yAxis: {                                                                
                title: {                                                            
                    text: 'Value'                                                   
                },                                                                  
                plotLines: [{                                                       
                    value: 0,                                                       
                    width: 1,                                                       
                    color: '#808080'                                                
                }]                                                                  
            },                                                                      
            tooltip: {                                                              
                formatter: function() {                                             
                        return '<b>'+ this.series.name +'</b><br>'+                
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br>'+
                        Highcharts.numberFormat(this.y, 2);                         
                }                                                                   
            },                                                                      
            legend: {                                                               
                enabled: false                                                      
            },                                                                      
            exporting: {                                                            
                enabled: true                                                      
            },                                                                      
            series: [{                                                              
                name: 'running Threads',                                                
                data: (function() {                                                 
                    // generate an array of random data                             
                    var data = [],                                                  
                        time = (new Date()).getTime(),                              
                        i;                                                          
                                                                                    
                    for (i = -19; i <= 0; i++) {                                    
                        data.push({                                                 
                            x: time + i * 1000,                                     
                            y: Math.random()                                        
                        });                                                         
                    }                                                               
                    return data;                                                    
                })()                                                                
            },
            {                                                              
                name: 'finished Threads',                                                
                data: (function() {                                                 
                    // generate an array of random data                             
                    var data = [],                                                  
                        time = (new Date()).getTime(),                              
                        i;                                                          
                                                                                    
                    for (i = -19; i <= 0; i++) {                                    
                        data.push({                                                 
                            x: time + i * 1000,                                     
                            y: Math.random()                                        
                        });                                                         
                    }                                                               
                    return data;                                                    
                })()                                                                
            }
            
            ]                                                                      
        });                                                                         
    }
    $(function(){
    	showThreadChart();
    	showThroughputChart();
    	t = setInterval(start,refreshTime);
     }); 
   </script>
</head>
<body>
<input type="hidden" value="${taskId }" id="taskId">
<form class="form-inline definewidth m20" action="select" method="post"> 
  <p><font size=5>监控状态：</font><span id="init"><font size=5 color="blue">正在初始化.....</font></span>&nbsp;&nbsp;&nbsp;<span id="save"></span></p>
  <p><span id="running"></span></p>
  <p><span id="startedThreads"></span> <span id="activeThreads"></span> <span id="finishedThreads"></span> <span id="totalThreads"></span></p>  
  <p><span id="startTime"></span> <span id="endTime"></span></p>
  <p><span id="resultCal"></span></p>
  <p><span id="monitedSlaves"></span></p>
  <p><span id="summary"></span></p>
  <hr>
  <div id="container" style="min-width: 310px; height:200px; margin: 0 auto"></div>
  <hr>
  <div id="throughput" style="min-width: 310px; height:400px; margin: 0 auto"></div>
  <hr>
  <div id="logContent"></div>
</form>    
</body>
</html>
