package org.phoenix.node.servlet;

import java.io.IOException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.threads.JMeterContextService;
import org.phoenix.jmeter.utils.FlushQueue;
import org.phoenix.jmeter.utils.LogPrint;
import org.phoenix.model.JmeterListenerBean;
import org.phoenix.node.action.ActionFactory;
import org.phoenix.node.action.ActionHandler;
import org.phoenix.node.action.JmeterHTTPAction;
import org.phoenix.node.compiler.DynamicEngine;
import org.phoenix.node.dto.AjaxObj;
import org.phoenix.node.dto.TaskDataDTO;
import org.phoenix.node.dto.TaskType;
import org.phoenix.node.util.WriteResponse;

import com.alibaba.fastjson.JSON;

public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExecutorService executorService;
	private ExecutorService apiService;
	private CompletionService<AjaxObj> threadPool;
	private Future<AjaxObj> future;
	private String contentPath;
	private JmeterListenerBean lisModel;
    public Servlet() {
        super();
    }
    @Override
	public void init(ServletConfig config) throws ServletException {
		executorService = Executors.newSingleThreadExecutor();	
		apiService = Executors.newCachedThreadPool();
		threadPool = new ExecutorCompletionService<AjaxObj>(executorService);
		future = threadPool.submit(new ActionHandler());
		contentPath = config.getServletContext().getRealPath("");
	}
	@Override
	public void destroy() {
		Enumeration<Driver> drivers = DriverManager.getDrivers();  
		while (drivers.hasMoreElements()) {  
		    Driver driver = drivers.nextElement();  
		    try {  
		        DriverManager.deregisterDriver(driver);   
		    } catch (SQLException e) {  
		    	e.printStackTrace();
		    }  
		}  
		executorService.shutdownNow();
		apiService.shutdownNow();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String status = request.getParameter("requestType")==null?"getStatus":request.getParameter("requestType");
		int taskId = request.getParameter("taskId")==null?-1:Integer.parseInt(request.getParameter("taskId"));
		switch(status){
		case "getStatus":
			if(future.isDone()) WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(1,"当前执行机处于空闲状态")));
			else WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(0,"该分机当前正在执行测试任务，请选择其他执行机！")));
			break;
		case "getJmeterQueue":
			if(taskId == lisModel.getId())WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(1,FlushQueue.getInstance().queueString())));
			else WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(0,"当前任务暂无即时结果")));
			break;
		case "getJmeterState":
			if(taskId == lisModel.getId()){
				lisModel.setActiveThreads(JMeterContextService.getThreadCounts().activeThreads);
				lisModel.setFinishedThreads(JMeterContextService.getThreadCounts().finishedThreads);
				lisModel.setLocalIP(request.getServerName());
				lisModel.setQueueString(FlushQueue.getInstance().queueString()==null?"":FlushQueue.getInstance().queueString().replace("--", "<br>"));
				lisModel.setRunning(StateListener.isRunning());
				lisModel.setSetNumThreads(JMeterContextService.getNumberOfThreads());
				lisModel.setStartedThreads(JMeterContextService.getThreadCounts().startedThreads);
				lisModel.setTotalThreads(JMeterContextService.getTotalThreads());
				lisModel.setStartTime(StateListener.getStartTime());
				lisModel.setEndTime(StateListener.getEndTime());
				lisModel.setSummary(Summariser.getSummary());
				lisModel.setMonitedSlaves(StateListener.getSlaveMetrics()==null?"":StateListener.getSlaveMetrics());
				lisModel.setResultCal(ResultCollector.getRESULT_CAL().replace(",", "<br>"));
				WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(1,"JmeterStatus:",lisModel)));
			} else {
				WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(0,"当前任务无即时数据")));
			}

			break;
		case "getJmeterLog":
			if(taskId == lisModel.getId())WriteResponse.writeXml(response, LogPrint.printBr(DynamicEngine.getInstance().getWebInfPath()+"result.jtl"));
			else WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(0,"当前任务暂无即时结果，日志文件中的内容不属于当前任务")));
			break;
		default:;
		}
	}
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionFactory actionFactory = new ActionFactory();
		TaskDataDTO taskDataDTO = new TaskDataDTO();
		TaskType taskType = Enum.valueOf(TaskType.class, request.getParameter("taskType"));
		taskDataDTO.setTaskId(Integer.parseInt(request.getParameter("taskId")));
		taskDataDTO.setTaskType(taskType);
		taskDataDTO.setContentPath(contentPath);
		actionFactory.setTaskDataDTO(taskDataDTO);
		if(taskType == TaskType.INTERFACE_CASE){
			checkResult(response,apiService.submit(actionFactory));
		}else if(taskType == TaskType.JMETER_HTTP_CASE){
			String operType = request.getParameter("operType");
			if(operType.equals("stop")){
				StandardJMeterEngine.stopEngine();
				future = threadPool.submit(new JmeterHTTPAction(taskDataDTO.getTaskId(),0));
				WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(2,"JMeter测试任务 [ 任务ID："+taskDataDTO.getTaskId()+" ]，停止成功！")));
			} else if(operType.equals("start")){
				if(!StateListener.isRunning()){
					lisModel = new JmeterListenerBean();
					lisModel.setId(taskDataDTO.getTaskId());
					future = threadPool.submit(new JmeterHTTPAction(taskDataDTO.getTaskId(),1));
					checkResult(response,future);
				} else {
					WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(1,"该分机当前正在执行JMeter用例任务，请选择其他负载机！")));
				}
			}
		}else{
			if(future.isDone()){
				future = threadPool.submit(actionFactory);
				checkResult(response,future);
			} else {
				WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(1,"该分机当前正在执行WEBUI测试任务，请选择其他执行机！")));
			}
		}
	}
    private void checkResult(HttpServletResponse response,Future<AjaxObj> r){
		try {
			WriteResponse.writeXml(response, JSON.toJSONString(r.get(500, TimeUnit.MILLISECONDS)));
		} catch (TimeoutException e1) {
			WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(2,"任务发布成功")));
		} catch (Exception e) {
			WriteResponse.writeXml(response, JSON.toJSONString(new AjaxObj(0,e.getMessage()==null?"任务执行失败":e.getMessage())));
		}
    }
}
