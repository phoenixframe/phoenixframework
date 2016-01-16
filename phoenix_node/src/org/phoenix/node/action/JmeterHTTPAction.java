package org.phoenix.node.action;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.testelement.TestStateListener;
import org.phoenix.dao.JmeterBeanDao;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.jmeter.core.PhoenixJmeterLauncher;
import org.phoenix.model.PhoenixJmeterBean;
import org.phoenix.node.compiler.DynamicEngine;
import org.phoenix.node.dto.AjaxObj;
import org.phoenix.node.servlet.StateListener;

/**
 * 执行jmeter用例
 * @author mengfeiyang
 *
 */
public class JmeterHTTPAction implements Callable<AjaxObj>{
	private int taskId;
	private int task;
	private JmeterBeanDao perfDao = new JmeterBeanDao();
	public JmeterHTTPAction(int taskId,int task){
		this.taskId = taskId;
		this.task = task;
	}
	
	public AjaxObj updateStatus(){
		PhoenixJmeterBean oModel = perfDao.load(taskId);
		oModel.setStatus(TaskStatusType.STOP.name());
		perfDao.update(oModel);
		return new AjaxObj(1,"状态已更新");
	}
	
	public AjaxObj startAction() {
		PhoenixJmeterBean model = null;
		try{
			model = perfDao.loadModel(taskId);
			/*model.setCaseName("百度测试");
			model.setNumThreads("5");
			model.setRampTime("0");
			model.setScheduler("false");
			model.setSampleErrorControl("continue");
			model.setContinueForever("false");
			model.setControllerLoops("10");
			
			model.setStartTime("1111");
			model.setEndTime("22222");
			model.setDuration("10");
			model.setDelayTime("0");
			model.setDelayedStart("false");
			
			model.setDomainURL("www.baidu.com");
			model.setConnectTimeOut("1000");
			model.setResponseTimeOut("1000");
			model.setContentEncoding("UTF-8");
			model.setRequestProtocol("http");
			model.setRequestMethod("GET");
			
			model.setCheckPointType("2");
			model.setCheckPointValue("百度一下");
			
			model.setClearCache("false");
			
			model.setEmailAttemper("false");
			model.setSuccessLimit("2");
			model.setFailureLimit("2");
			//model.setSuccessSubject("");
			//model.setFailureSubject("");
			model.setFromAddress("fromaddress");
			model.setSmtpHost("1111");
			model.setSendTo("2222");
			model.setEmailServerLoginName("333");
			model.setEmailServerLoginPassword("");
			model.setAuthType("SSL");*/
			String WEB_INF = DynamicEngine.getInstance().getWebInfPath();
			String uploadFile = "";
			if(model.getEnableDataSet().equals("on") && !"".equals(model.getFilePath()) && model.getFullUrl().contains("[")){
				HashMap<String,String> paramData = new HashMap<String,String>();
				URI uri = URI.create(model.getFullUrl().replace("[", "").replace("]", ""));
				uploadFile = model.getFilePath().endsWith("csv")?WEB_INF+"JmeterParams.csv":WEB_INF+"JmeterParams.data";
				String variableNames = "";
				if(uri.getQuery().contains("&")){
					for(String var : uri.getQuery().split("&")){
						if(var.contains("=")){
							String variValue = "";
							try{variValue = URLEncoder.encode(var.split("=")[1],"UTF-8");}catch(ArrayIndexOutOfBoundsException ex){}
							variableNames += variValue+",";
							paramData.put(var.split("=")[0], "${"+variValue+"}");
						}
					}
					variableNames = variableNames.substring(0, variableNames.length()-1);
				} else {
					if(uri.getQuery().contains("=")){
						try{variableNames = URLEncoder.encode(uri.getQuery().split("=")[1],"UTF-8");}catch(ArrayIndexOutOfBoundsException ee){};
						paramData.put(uri.getQuery().split("=")[0], "${"+variableNames+"}");
					}
				}
				model.setVariableNames(variableNames);
				model.setParams(paramData);
				File file = new File(uploadFile);
				if(file.exists())file.delete();
				FileUtils.copyURLToFile(new URL(model.getFilePath()), file);
				model.setFileName(uploadFile);
			} else {
				URI uri = URI.create(model.getFullUrl());
				if(uri.getQuery()!=null){
					HashMap<String,String> paramData = new HashMap<String,String>();
					if(uri.getQuery().contains("&")){
						String[] para = uri.getQuery().split("&");
						for(String pa:para){
							if(pa.contains("=")){
								String varValue = "";
								try{varValue = URLEncoder.encode(pa.split("=")[1],"UTF-8");}catch(ArrayIndexOutOfBoundsException e){}
								paramData.put(pa.split("=")[0], varValue);
							}
						}
					}else{
						if(uri.getQuery().contains("=")){
							String varValue = "";
							try{varValue = URLEncoder.encode(uri.getQuery().split("=")[1],"UTF-8");}catch(ArrayIndexOutOfBoundsException e){}
							paramData.put(uri.getQuery().split("=")[0], varValue);
						}
					}
					model.setParams(paramData);
				}
			}
			
			TestStateListener listener = new StateListener(taskId);
			PhoenixJmeterLauncher laucher = new PhoenixJmeterLauncher(listener);
			int r = laucher.runTests(model, WEB_INF+"result.jtl",WEB_INF+"phoenix.jmx", WEB_INF,"phoenix.ftl");
			if (r == 1){
				model.setStatus(TaskStatusType.RUNNING.name());
				perfDao.update(model);
				return new AjaxObj(2,"启动JmeterHTTPAction用例完成");
			}else {
				model.setStatus(TaskStatusType.FAIL.name());
				perfDao.update(model);
				return new AjaxObj(0,"启动JmeterHTTPAction用例失败,请检查参数配置"); 
			}	
		}catch(Exception e){
			model.setStatus(TaskStatusType.FAIL.name());
			perfDao.update(model);
			return new AjaxObj(0,"启动JmeterHTTPAction用例时发生异常，异常信息："+e.getMessage()); 
		}
	}
	
	@Override
	public AjaxObj call() throws Exception {
		return task == 1?startAction():updateStatus();
	}
}
