package org.phoenix.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IBatchLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/chart")
@AuthClass
public class ChartController {
	private IBatchLogService batchLogService;

	public IBatchLogService getBatchLogService() {
		return batchLogService;
	}
	@Resource
	public void setBatchLogService(IBatchLogService batchLogService) {
		this.batchLogService = batchLogService;
	}

	@RequestMapping(value="/CASE",method=RequestMethod.GET)
	public String getCaseStatistics(Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("blist", batchLogService.getBatchLogListByType("CASE",u.getId()));
		return "chart/sta_case";
	}
	
	@RequestMapping(value="/CASE/{id}",method=RequestMethod.GET)
	public String getCaseStatistics(@PathVariable Integer id,Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("lbean", batchLogService.getBatchLogBean(id));
		model.addAttribute("blist", batchLogService.getBatchLogListByType("CASE",u.getId()));
		return "chart/sta_case";
	}
	
	@RequestMapping(value="/SCENARIO",method=RequestMethod.GET)
	public String getScenarioStatistics(Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("blist", batchLogService.getBatchLogListByType("SCENARIO",u.getId()));
		return "chart/sta_scenario";
	}
	
	@RequestMapping(value="/SCENARIO/{id}",method=RequestMethod.GET)
	public String getScenarioStatistics(@PathVariable Integer id,Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("lbean", batchLogService.getBatchLogBean(id));
		model.addAttribute("blist", batchLogService.getBatchLogListByType("SCENARIO",u.getId()));
		return "chart/sta_scenario";
	}
}
