package org.phoenix.node.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.threads.JMeterContextService;
import org.phoenix.dao.JmeterBeanDao;
import org.phoenix.dao.PerfBatchLogDao;
import org.phoenix.dao.PerfLogDao;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.jmeter.perfmon.PerfMonCollector;
import org.phoenix.jmeter.utils.FlushQueue;
import org.phoenix.model.PerfBatchLogModel;
import org.phoenix.model.PerfLogModel;
import org.phoenix.model.PhoenixJmeterBean;
import org.phoenix.utils.GetNow;

public class StateListener implements TestStateListener{
	private static boolean isRunning;
	private static String endTime; 
	private static String startTime;
	private static String slaveMetrics = "<br>";
	private Timer addLogTimer = new Timer();
	private int taskId;
	private PerfBatchLogModel perfBatchLogModel;
	private PerfBatchLogDao perfBatchDao = new PerfBatchLogDao();
	private PerfLogDao perfLogDao = new PerfLogDao();
	private JmeterBeanDao jmeterBeanDao = new JmeterBeanDao();
	private static List<PerfMonCollector> monitedList;
	public StateListener(int taskId){
		this.taskId = taskId;
	}
	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		StateListener.isRunning = isRunning;
	}

	public static String getEndTime() {
		return endTime;
	}

	public static String getStartTime() {
		return startTime;
	}
	public static void setStartTime(String startTime) {
		StateListener.startTime = startTime;
	}
	public static void setEndTime(String endTime) {
		StateListener.endTime = endTime;
	}
	private String rs(String r){
		return r.split(":")[1];
	}
	
	public static String getSlaveMetrics(){
		slaveMetrics = "";
		if(monitedList !=null){
			for(PerfMonCollector pm:monitedList){
				if(!pm.getMetricsResources().startsWith("hostName")){
					monitedList.remove(pm);
				}else{
					slaveMetrics += pm.getMetricsResources()+"<br>";
				}
			}
		}
		return slaveMetrics;
	}
	
	private void stopMonitors(){
		for(PerfMonCollector pm:monitedList){
			pm.testEnded();
		}
	}
	
	private void addLog(){
		String[] datas = ResultCollector.getRESULT_CAL().split(",");
		PerfLogModel perfLogModel = new PerfLogModel();

		perfLogModel.setSetNumThreads(JMeterContextService.getTotalThreads());
		perfLogModel.setAvgPageBytes(rs(datas[0]));
		perfLogModel.setBytesPerSecond(rs(datas[1]));
		perfLogModel.setTotalCount(rs(datas[2]));
		perfLogModel.setErrorPercentage(rs(datas[3]));
		perfLogModel.setKbPerSecond(rs(datas[4]));
		perfLogModel.setMaxResponseTime(rs(datas[5]));
		perfLogModel.setMeanResponseTime(rs(datas[6]));
		perfLogModel.setMinResponseTime(rs(datas[7]));
		perfLogModel.setTotalBytes(rs(datas[10]));
		perfLogModel.setSummary(Summariser.getSummary());
		perfLogModel.setPerfBatchLogModel(perfBatchLogModel);
		perfLogModel.setMonitedSlaveMetrics(slaveMetrics);
		
		perfLogDao.add(perfLogModel);
	}
	
	private void addLogTimer() {
		addLogTimer.schedule(new TimerTask() {
			@Override
			public void run() {	
				addLog();
			}
		}, 10000, 10000);
	}

	@Override
	public void testStarted() {
		System.out.println("now test starting...........");	
		setStartTime(GetNow.getCurrentTime());
		Summariser.setSummary("");
		slaveMetrics = "";
		PhoenixJmeterBean phoenixJmeterBean = jmeterBeanDao.load(taskId);
		monitedList = new ArrayList<PerfMonCollector>();
		PerfBatchLogModel perfBatchModel = new PerfBatchLogModel();
		perfBatchModel.setPhoenixJmeterBean(phoenixJmeterBean);
		perfBatchModel.setStartTime(startTime);
		perfBatchLogModel = perfBatchDao.add(perfBatchModel);
		if(phoenixJmeterBean.getMonitedSlaves() != null && !phoenixJmeterBean.getMonitedSlaves().equals("")){
			if(phoenixJmeterBean.getMonitedSlaves().contains(";")){
				String[] monitedSlaves = phoenixJmeterBean.getMonitedSlaves().split(";");
				for(String sl : monitedSlaves){
					PerfMonCollector perfMonCollector = new PerfMonCollector(sl);
					int res = perfMonCollector.testStarted();
					if(res == 1)monitedList.add(perfMonCollector);
				}
			} else {
				PerfMonCollector perfMonCollector = new PerfMonCollector(phoenixJmeterBean.getMonitedSlaves());
				int res = perfMonCollector.testStarted();
				if(res == 1)monitedList.add(perfMonCollector);
			}
		}
		setRunning(true);
		setEndTime("");
		addLogTimer();
		
	}
	
	@Override
	public void testEnded() {
		System.out.println("now test end...........");
		FlushQueue.getInstance().releaseQueue();
		StandardJMeterEngine.stopEngine();
		PhoenixJmeterBean jmeterBean = jmeterBeanDao.loadModel(taskId);
		jmeterBean.setStatus(TaskStatusType.STOP.name());
		jmeterBeanDao.update(jmeterBean);
		setRunning(false);
		setEndTime(GetNow.getCurrentTime()); 
		addLog();
		perfBatchLogModel.setEndTime(endTime);
		perfBatchLogModel.setSummary(Summariser.getSummary());
		perfBatchLogModel.setContinued(Summariser.getSummary().split("in")[1].split("=")[0].trim());
		perfBatchDao.update(perfBatchLogModel);
		addLogTimer.cancel();
		stopMonitors();
	}

	@Override
	public void testEnded(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testStarted(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
