package org.phoenix.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.phoenix.enums.TaskStatusType;
import org.phoenix.model.PerfBatchLogModel;
import org.phoenix.model.PerfLogModel;
import org.phoenix.model.PhoenixJmeterBean;
import org.phoenix.utils.GetNow;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.dto.AjaxObj;
import org.phoenix.web.dto.ChartDTO;
import org.phoenix.web.dto.ChartDataDTO;
import org.phoenix.web.dto.PerformanceDTO;
import org.phoenix.web.model.SlaveModel;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IPerfBatchLogService;
import org.phoenix.web.service.IPerfLogService;
import org.phoenix.web.service.IPerformanceService;
import org.phoenix.web.service.ISlaveService;
import org.phoenix.web.util.EnumUtils;
import org.phoenix.web.util.HttpRequestSender;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
/**
 * 性能测试任务配置控制器
 * @author mengfeiyang
 *
 */
@Controller
@RequestMapping("/perf")
@AuthClass("login")
public class PerformanceController {
	private IPerformanceService performanceService;
	private IPerfBatchLogService perfBatchLogService;
	private IPerfLogService perfLogService;
	private ISlaveService slaveService;

	public ISlaveService getSlaveService() {
		return slaveService;
	}
	@Resource
	public void setSlaveService(ISlaveService slaveService) {
		this.slaveService = slaveService;
	}
	public IPerformanceService getPerformanceService() {
		return performanceService;
	}
	@Resource
	public void setPerformanceService(IPerformanceService performanceService) {
		this.performanceService = performanceService;
	}
	
	public IPerfBatchLogService getPerfBatchLogService() {
		return perfBatchLogService;
	}
	@Resource
	public void setPerfBatchLogService(IPerfBatchLogService perfBatchLogService) {
		this.perfBatchLogService = perfBatchLogService;
	}
	
	public IPerfLogService getPerfLogService() {
		return perfLogService;
	}
	@Resource
	public void setPerfLogService(IPerfLogService perfLogService) {
		this.perfLogService = perfLogService;
	}
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute("status",EnumUtils.enumProp2NameMap(TaskStatusType.class, "name"));
		model.addAttribute("datas", performanceService.getJmeterBeanPager(u.getId()));
		return "jmeter/list";
	}
	
	@RequestMapping(value="/selist",method=RequestMethod.POST)
	public String selist(String keyWord,Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute("keyword", keyWord);
		model.addAttribute("datas", performanceService.getJmeterBeansPagerByKeyWord(u.getId(),keyWord));
		return "jmeter/list";
	}
	
	private void initDatas(Model model){
		LinkedHashMap<String,String> checkPointTypes = new LinkedHashMap<String,String>();
		checkPointTypes.put("1", "匹配");
		checkPointTypes.put("2", "包含");
		checkPointTypes.put("8", "等于");
		checkPointTypes.put("16", "substring");
		checkPointTypes.put("6", "不包含");
		checkPointTypes.put("12", "不等于");
		checkPointTypes.put("20", "非substring");
		
		LinkedHashMap<String,String> failStrategy = new LinkedHashMap<String,String>();
		failStrategy.put("continue", "继续执行");
		failStrategy.put("stopthread", "停止当前线程");
		failStrategy.put("stoptest", "停止测试");
		failStrategy.put("stoptestnow", "立即停止场景");
		
		model.addAttribute("checkPointTypes", checkPointTypes);
		model.addAttribute("failStrategy", failStrategy);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");

		model.addAttribute(new PerformanceDTO());
		model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
		initDatas(model);
		return "jmeter/edit";
	}

	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid PerformanceDTO performanceDTO,BindingResult br,Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		if(br.hasErrors()){
			model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
			return "jmeter/edit";
		}
		PhoenixJmeterBean jmeterBean = new PhoenixJmeterBean();
		BeanUtils.copyProperties(performanceDTO, jmeterBean);
		jmeterBean.setUserId(u.getId());
		jmeterBean.setStatus(TaskStatusType.NOT_RUNNING.name());
		String url = performanceDTO.getDomainURL();
		if(url.substring(0, 5).trim().equals("https")){
			jmeterBean.setRequestProtocol("https");
		}else if(url.substring(0, 4).trim().equals("http")){
			jmeterBean.setRequestProtocol("http");
		} else {
			jmeterBean.setRequestProtocol("http");
		}
		
		URI uri = URI.create(url);
		jmeterBean.setDomainURL(uri.getHost());
		jmeterBean.setUrlPort(uri.getPort()+"");
		jmeterBean.setUrlPath(uri.getPath());
		jmeterBean.setFullUrl(url);
		jmeterBean.setSuccessSubject("Jmeter用例："+jmeterBean.getCaseName()+" 至少有 "+jmeterBean.getSuccessLimit()+" 个项目成功。负载机："+slaveService.getModel(jmeterBean.getSlaveId()).getSlaveIP());
		jmeterBean.setFailureSubject("Jmeter用例："+jmeterBean.getCaseName()+" 至少有 "+jmeterBean.getFailureLimit()+" 个项目失败。负载机："+slaveService.getModel(jmeterBean.getSlaveId()).getSlaveIP());
		if(performanceDTO.getStartTime().trim().equals(""))jmeterBean.setStartTime(System.currentTimeMillis()+"");
		if(performanceDTO.getEndTime().trim().equals(""))jmeterBean.setEndTime(System.currentTimeMillis()+"");

		performanceService.add(jmeterBean);
		
		return "redirect:/perf/list";
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable Integer id){
		performanceService.delete(id);
		return "redirect:/perf/list";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute(new PerformanceDTO());
		model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
		model.addAttribute("perfBean", performanceService.getJmeterBean(id));
		initDatas(model);
		return "jmeter/edit";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid PerformanceDTO performanceDTO,BindingResult br,Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		if(br.hasErrors()){
			model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
			return "jmeter/edit";
		}
		PhoenixJmeterBean jmeterBean = performanceService.getJmeterBean(id);
		BeanUtils.copyProperties(performanceDTO, jmeterBean);
		jmeterBean.setUserId(u.getId());
		String url = performanceDTO.getDomainURL();
		
		if(url.substring(0, 5).trim().equals("https")){
			jmeterBean.setRequestProtocol("https");
		}else if(url.substring(0, 4).trim().equals("http")){
			jmeterBean.setRequestProtocol("http");
		} else {
			jmeterBean.setRequestProtocol("http");
		}
		
		URI uri = URI.create(url);
		jmeterBean.setDomainURL(uri.getHost());
		jmeterBean.setUrlPort(uri.getPort()+"");
		jmeterBean.setUrlPath(uri.getPath());
		jmeterBean.setFullUrl(url);
		jmeterBean.setSuccessSubject("Jmeter用例："+jmeterBean.getCaseName()+" 至少有 "+jmeterBean.getSuccessLimit()+" 个项目成功。负载机："+slaveService.getModel(jmeterBean.getSlaveId()).getSlaveIP());
		jmeterBean.setFailureSubject("Jmeter用例："+jmeterBean.getCaseName()+" 至少有 "+jmeterBean.getFailureLimit()+" 个项目失败。负载机："+slaveService.getModel(jmeterBean.getSlaveId()).getSlaveIP());
		if(performanceDTO.getStartTime().trim().equals(""))jmeterBean.setStartTime(System.currentTimeMillis()+"");
		if(performanceDTO.getEndTime().trim().equals(""))jmeterBean.setEndTime(System.currentTimeMillis()+"");

		performanceService.update(jmeterBean);
		
		return "redirect:/perf/list";
	}
	
	@RequestMapping(value="/start/{id}",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String start(@PathVariable Integer id){
		PhoenixJmeterBean jmeterBean = performanceService.getJmeterBean(id);
		SlaveModel slave = slaveService.getModel(jmeterBean.getSlaveId());
		String url = "http://"+slave.getSlaveIP()+"/phoenix_node/action.do?taskId="+id+"&taskType=JMETER_HTTP_CASE&operType=start";
		try {
			return HttpRequestSender.getResponseByPost(url);
		} catch (Exception e) {
			return JSON.toJSONString(new AjaxObj(0,e.getMessage()));
		}
	}
	
	@RequestMapping(value="/stop/{id}",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String stop(@PathVariable Integer id){
		PhoenixJmeterBean jmeterBean = performanceService.getJmeterBean(id);
		SlaveModel slave = slaveService.getModel(jmeterBean.getSlaveId());
		String url = "http://"+slave.getSlaveIP()+"/phoenix_node/action.do?taskId="+id+"&taskType=JMETER_HTTP_CASE&operType=stop";
		try {
			return HttpRequestSender.getResponseByPost(url);
		} catch (Exception e) {
			return JSON.toJSONString(new AjaxObj(0,e.getMessage()));
		}
	}
	
	@RequestMapping(value="/delperf/{id1}/{id2}",method=RequestMethod.GET)
	public String delHistory(@PathVariable Integer id1,@PathVariable Integer id2){
		perfBatchLogService.deleteBatchLog(id1);
		return "redirect:/perf/history/"+id2;
	}
	
	private String getResponseTime(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> responseDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("响应时间统计","日期："+GetNow.getCurrentTime(),"响应时间（ms）","ms");
		ChartDataDTO maxChartData = new ChartDataDTO("maxResponseTime");
		ChartDataDTO meanChartData = new ChartDataDTO("meanResponseTime");
		ChartDataDTO minChartData = new ChartDataDTO("minResponseTime");
		double[] maxDataArray = new double[size];
		double[] meanDataArray = new double[size];
		double[] minDataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			maxDataArray[i] = Double.parseDouble(perfLogModel.getMaxResponseTime());
			meanDataArray[i] = Double.parseDouble(perfLogModel.getMeanResponseTime());
			minDataArray[i] = Double.parseDouble(perfLogModel.getMinResponseTime());
			xDataArray[i] = (i+1)+"";
		}
		maxChartData.setData(maxDataArray);
		meanChartData.setData(meanDataArray);
		minChartData.setData(minDataArray);
		responseDataList.add(maxChartData);
		responseDataList.add(meanChartData);
		responseDataList.add(minChartData);
		chartDTO.setChartDataList(responseDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	
	private String getBytesPerSecond(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> bytesPerSecondDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("吞吐量（bytes/s）","日期："+GetNow.getCurrentTime(),"吞吐量：bytes","bytes");
		ChartDataDTO chartData = new ChartDataDTO("bytesPerSecond");
		double[] dataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			dataArray[i] = Double.parseDouble(perfLogModel.getBytesPerSecond());
			xDataArray[i] = (i+1)+"";
		}
		chartData.setData(dataArray);
		bytesPerSecondDataList.add(chartData);
		chartDTO.setChartDataList(bytesPerSecondDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	
	private String kbPerSecond(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> kbPerSecondDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("吞吐量（kb/s）","日期："+GetNow.getCurrentTime(),"吞吐量：kb","bytes");
		ChartDataDTO chartData = new ChartDataDTO("kbPerSecond");
		double[] dataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			dataArray[i] = Double.parseDouble(perfLogModel.getKbPerSecond());
			xDataArray[i] = (i+1)+"";
		}
		chartData.setData(dataArray);
		kbPerSecondDataList.add(chartData);
		chartDTO.setChartDataList(kbPerSecondDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	
	private String totalBytes(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> totalBytesDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("总吞吐量统计","日期："+GetNow.getCurrentTime(),"总吞吐量：bytes","bytes");
		ChartDataDTO chartData = new ChartDataDTO("totalBytes");
		double[] dataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			dataArray[i] = Double.parseDouble(perfLogModel.getTotalBytes());
			xDataArray[i] = (i+1)+"";
		}
		chartData.setData(dataArray);
		totalBytesDataList.add(chartData);
		chartDTO.setChartDataList(totalBytesDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	
	private String totalCounts(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> totalCountsDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("总通过事物数统计","日期："+GetNow.getCurrentTime(),"总通过事物数","");
		ChartDataDTO chartData = new ChartDataDTO("totalBytes");
		double[] dataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			dataArray[i] = Double.parseDouble(perfLogModel.getTotalCount());
			xDataArray[i] = (i+1)+"";
		}
		chartData.setData(dataArray);
		totalCountsDataList.add(chartData);
		chartDTO.setChartDataList(totalCountsDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	
	private String errorPercentage(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> totalCountsDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("总通过事物失败百分比","日期："+GetNow.getCurrentTime(),"总通过事物失败百分比：%","%");
		ChartDataDTO chartData = new ChartDataDTO("errorPercentage");
		double[] dataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			dataArray[i] = Double.parseDouble(perfLogModel.getErrorPercentage());
			xDataArray[i] = (i+1)+"";
		}
		chartData.setData(dataArray);
		totalCountsDataList.add(chartData);
		chartDTO.setChartDataList(totalCountsDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	
	private String getAvgPageBytes(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDataDTO> avgPageBytesDataList = new ArrayList<ChartDataDTO>();
		ChartDTO chartDTO = new ChartDTO("平均页面字节数统计（bytes/page）","日期："+GetNow.getCurrentTime(),"平均页面字节数统计：bytes","bytes");
		ChartDataDTO chartData = new ChartDataDTO("avgPageBytes");
		double[] dataArray = new double[size];
		String[] xDataArray = new String[size];
		
		for(int i=0;i<size;i++){
			PerfLogModel perfLogModel = perfLogList.get(i);
			dataArray[i] = Double.parseDouble(perfLogModel.getAvgPageBytes());
			xDataArray[i] = (i+1)+"";
		}
		chartData.setData(dataArray);
		avgPageBytesDataList.add(chartData);
		chartDTO.setChartDataList(avgPageBytesDataList);
		chartDTO.setXdata(xDataArray);
		
		return JSON.toJSONString(chartDTO);
	}
	private String rs(String r){
		return r.split(":")[1];
	}
	private String[] ra(String r){
		return rs(r).replace("[", "").replace("]", "").split(",");
	}
	private String monitedMetrics(List<PerfLogModel> perfLogList){
		int size = perfLogList.size();
		List<ChartDTO> chartDTOList = new ArrayList<ChartDTO>();
		PerfLogModel perfLogModel = perfLogList.get(0);
		if(perfLogModel.getMonitedSlaveMetrics()!=null && !perfLogModel.getMonitedSlaveMetrics().equals("") && !perfLogModel.getMonitedSlaveMetrics().equals("<br>") && perfLogModel.getMonitedSlaveMetrics().contains("<br>")){
			String[] metricsArray = perfLogModel.getMonitedSlaveMetrics().split("<br>");
			String titleSlave  = "";
			for(int i=0;i<metricsArray.length;i++){
				if(!metricsArray[i].equals("null")){
					titleSlave += "<a href=\"javascript:showmetrics("+i+");\">"+metricsArray[i].split("_")[0].split(":")[1]+"</a>&nbsp;&nbsp;&nbsp;";
				}
			}
			for(int i=0;i<metricsArray.length;i++){
				if(!metricsArray[i].equals("") && !metricsArray[i].equals("null")){
					List<ChartDataDTO> metricsDataList = new ArrayList<ChartDataDTO>();
					ChartDTO chartDTO = new ChartDTO("机器资源监控 [ "+titleSlave+" ]",metricsArray[i].split("_")[0],metricsArray[i].split("_")[0]+"资源","");
					ChartDataDTO cpuChartData = new ChartDataDTO("CPU");
					ChartDataDTO memChartData = new ChartDataDTO("MEM");
					ChartDataDTO swapinChartData = new ChartDataDTO("swap in");
					ChartDataDTO swapoutChartData = new ChartDataDTO("swap out");
					ChartDataDTO diskinChartData = new ChartDataDTO("disk in");
					ChartDataDTO diskoutChartData = new ChartDataDTO("disk out");
					ChartDataDTO netinChartData = new ChartDataDTO("network in");
					ChartDataDTO netoutChartData = new ChartDataDTO("network out");
					double[] cpuDataArray = new double[size];
					double[] memDataArray = new double[size];
					double[] swapinDataArray = new double[size];
					double[] swapoutDataArray = new double[size];
					double[] diskinDataArray = new double[size];
					double[] diskoutDataArray = new double[size];
					double[] netinDataArray = new double[size];
					double[] netoutDataArray = new double[size];
					String[] xDataArray = new String[size];
					for(int j=0;j<size;j++){
						PerfLogModel perfLogModel2 = perfLogList.get(j);
						String[] dataBlocks = perfLogModel2.getMonitedSlaveMetrics().split("<br>")[i].split("_");
						cpuDataArray[j] = Double.parseDouble(rs(dataBlocks[1]));
						memDataArray[j] = Double.parseDouble(rs(dataBlocks[2]));
						swapinDataArray[j] = Double.parseDouble(ra(dataBlocks[3])[0]);
						swapoutDataArray[j] = Double.parseDouble(ra(dataBlocks[3])[1]);
						diskinDataArray[j] = Double.parseDouble(ra(dataBlocks[4])[0]);
						diskoutDataArray[j] = Double.parseDouble(ra(dataBlocks[4])[1]);
						netinDataArray[j] = Double.parseDouble(ra(dataBlocks[5])[0]);
						netoutDataArray[j] = Double.parseDouble(ra(dataBlocks[5])[1]);		
						xDataArray[j] = (j+1)+"";
					}
					cpuChartData.setData(cpuDataArray);
					memChartData.setData(memDataArray);
					swapinChartData.setData(swapinDataArray);
					swapoutChartData.setData(swapoutDataArray);
					diskinChartData.setData(diskinDataArray);
					diskoutChartData.setData(diskoutDataArray);
					netinChartData.setData(netinDataArray);
					netoutChartData.setData(netoutDataArray);
					
					metricsDataList.add(cpuChartData);
					metricsDataList.add(memChartData);
					metricsDataList.add(swapinChartData);
					metricsDataList.add(swapoutChartData);
					metricsDataList.add(diskinChartData);
					metricsDataList.add(diskoutChartData);
					metricsDataList.add(netinChartData);
					metricsDataList.add(netoutChartData);
					
					chartDTO.setChartDataList(metricsDataList);
					chartDTO.setXdata(xDataArray);
					chartDTOList.add(chartDTO);
				}
			}
		}
		return JSON.toJSONString(chartDTOList);
	}
	
	@RequestMapping(value="/showChart/{id}",method=RequestMethod.GET)
	public String showChart(@PathVariable Integer id,Model model){
		PerfBatchLogModel batchLogModel = perfBatchLogService.load(id);
		List<PerfLogModel> perfLogList = perfLogService.loadPerfLogModels(id);
		model.addAttribute("responseTimeData", getResponseTime(perfLogList));
		model.addAttribute("bytesPerSecondData", getBytesPerSecond(perfLogList));
		model.addAttribute("kbPerSecondData", kbPerSecond(perfLogList));
		model.addAttribute("totalBytesData", totalBytes(perfLogList));
		model.addAttribute("totalCountsData", totalCounts(perfLogList));
		model.addAttribute("errorPercentageData", errorPercentage(perfLogList));
		model.addAttribute("avgPageBytesData", getAvgPageBytes(perfLogList));
		model.addAttribute("metricsData", monitedMetrics(perfLogList));
		
		model.addAttribute("summery", "用例名称："+batchLogModel.getPhoenixJmeterBean().getCaseName()+",用例id："+batchLogModel.getPhoenixJmeterBean().getId()+"，"+perfLogList.get(perfLogList.size()-1).getSummary());
		
		return "jmeter/chart";
	}
	
	@RequestMapping(value="/history/{id}",method=RequestMethod.GET)
	public String showChartList(@PathVariable Integer id,Model model){
		model.addAttribute("datas", perfBatchLogService.getBatchLogPager(id));
		return "jmeter/history";
	}
	
	@RequestMapping(value="/monitor/{id}",method=RequestMethod.GET)
	public String monitor(@PathVariable Integer id,Model model){
		model.addAttribute("taskId", id);
		return "jmeter/monitor";
	}
	
	@RequestMapping(value="/monitorstate/{id}",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String monitorState(@PathVariable Integer id){
		PhoenixJmeterBean jmeterBean = performanceService.getJmeterBean(id);
		SlaveModel slave = slaveService.getModel(jmeterBean.getSlaveId());
		String url  = "http://"+slave.getSlaveIP()+"/phoenix_node/action.do?requestType=getJmeterState&taskId="+id;
		try {
			
			return 	HttpRequestSender.getResponseByGet(url);
		} catch (Exception e) {
			return JSON.toJSONString(new AjaxObj(0,e.getMessage()));
		}
	}

	@RequestMapping(value="/monitorlog/{id}",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String monitorLog(@PathVariable Integer id){
		PhoenixJmeterBean jmeterBean = performanceService.getJmeterBean(id);
		SlaveModel slave = slaveService.getModel(jmeterBean.getSlaveId());
		String url  = "http://"+slave.getSlaveIP()+"/phoenix_node/action.do?requestType=getJmeterLog&taskId="+id;
		try {
			return HttpRequestSender.getResponseByGet(url);
		} catch (Exception e) {
			return JSON.toJSONString(new AjaxObj(0,e.getMessage()));
		}
	}
}
