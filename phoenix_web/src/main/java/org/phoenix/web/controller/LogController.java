package org.phoenix.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.phoenix.web.enums.TaskType;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IBatchLogService;
import org.phoenix.web.service.ICaseLogService;
import org.phoenix.web.service.IScenarioLogService;
import org.phoenix.web.service.ITaskService;
import org.phoenix.web.service.IUnitLogService;
import org.phoenix.web.util.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 日志控制器
 * @author mengfeiyang
 *
 */
@Controller
@RequestMapping("/log")
public class LogController {
	private IBatchLogService batchLogService;
	private ICaseLogService caseLogService;
	private IUnitLogService unitLogService;
	private IScenarioLogService scenarioLogService;
	private ITaskService taskService;
	
	public IBatchLogService getBatchLogService() {
		return batchLogService;
	}
	@Resource
	public void setBatchLogService(IBatchLogService batchLogService) {
		this.batchLogService = batchLogService;
	}

	public ICaseLogService getCaseLogService() {
		return caseLogService;
	}
	@Resource
	public void setCaseLogService(ICaseLogService caseLogService) {
		this.caseLogService = caseLogService;
	}

	public IUnitLogService getUnitLogService() {
		return unitLogService;
	}
	@Resource
	public void setUnitLogService(IUnitLogService unitLogService) {
		this.unitLogService = unitLogService;
	}
	
	public IScenarioLogService getScenarioLogService() {
		return scenarioLogService;
	}
	@Resource
	public void setScenarioLogService(IScenarioLogService scenarioLogService) {
		this.scenarioLogService = scenarioLogService;
	}
	
	
	public ITaskService getTaskService() {
		return taskService;
	}
	@Resource
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	@RequestMapping("/batchlog/{id}")
	public String batchSelect(Model model,@PathVariable Integer id){
		model.addAttribute("batchId", id);
		model.addAttribute("datas", batchLogService.getBatchLogPagerById(id));
		return "log/batchlist";
	}
	
	@RequestMapping("/batchlist")
	public String batchList(Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute("types", EnumUtils.enumProp2NameMap(TaskType.class, "name"));
		model.addAttribute("datas", batchLogService.getBatchLogPager(u.getId()));
		return "log/batchlist";
	}
	@RequestMapping("/deletebatch/{id}")
	public String deleteBatchLog(@PathVariable Integer id){
		batchLogService.deleteBatchLog(id);
		taskService.updateBatchLogId(id);
		return "redirect:/log/batchlist";
	}
	
	@RequestMapping("/deletebatchs/{ids}")
	public String deleteBatchLogs(@PathVariable String ids){
		if(ids.contains(",")){
			String[] idarray = ids.trim().split(",");
			for(String id : idarray){
				batchLogService.deleteBatchLog(Integer.parseInt(id));
				taskService.updateBatchLogId(Integer.parseInt(id));
			}
		}else{
			batchLogService.deleteBatchLog(Integer.parseInt(ids.trim()));
			taskService.updateBatchLogId(Integer.parseInt(ids.trim()));
		}
		return "redirect:/log/batchlist";
	}
	
	@RequestMapping("/WEB_CASE/{id}")
	public String batchCaseList(@PathVariable Integer id,HttpSession session,Model model){
		model.addAttribute("logId", id);
		model.addAttribute("datas", caseLogService.getCaseLogPagerByBatchLog(id));
		return "log/bcaselist";
	}
	@RequestMapping("/INTERFACE_CASE/{id}")
	public String batchAPICaseList(@PathVariable Integer id,HttpSession session,Model model){
		model.addAttribute("logId", id);
		model.addAttribute("datas", caseLogService.getCaseLogPagerByBatchLog(id));
		return "log/bcaselist";
	}
	@RequestMapping("/MOBILE_CASE/{id}")
	public String batchMobileCaseList(@PathVariable Integer id,HttpSession session,Model model){
		model.addAttribute("logId", id);
		model.addAttribute("datas", caseLogService.getCaseLogPagerByBatchLog(id));
		return "log/bcaselist";
	}
	
	@RequestMapping("/scenCaseList/{id}")
	public String scenCaseList(@PathVariable Integer id,Model model){
		model.addAttribute("datas", caseLogService.getCaseLogPagerByScenarioLog(id));
		return "log/scaselist";
	}
	
	@RequestMapping("/WEB_SCENARIO/{id}")
	public String scenList(@PathVariable Integer id,Model model){
		model.addAttribute("batchLogId", id);
		model.addAttribute("datas", scenarioLogService.getLogPager(id));
		return "log/scenlist";
	}
	
	@RequestMapping("/deletecaselog/{sid}/{id}")
	public String deleteScenCaseLog(@PathVariable Integer sid,@PathVariable Integer id){
		caseLogService.deleteCaseLog(id);
		return "redirect:/log/scenCaseList/"+sid;
	}
	
	@RequestMapping("/unitLogList/{id}")
	public String unitLogList(@PathVariable Integer id,Model model){
		model.addAttribute("datas", unitLogService.getUnitLogPager(id));
		return "log/unitlist";
	}
}
