package org.phoenix.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.phoenix.web.model.TaskModel;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IScenarioService;
import org.phoenix.web.service.ITaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 业务结构树控制器
 * @author mengfeiyang
 *
 */
@Controller
@RequestMapping("/tree")
public class TreeController {
	private IScenarioService scenarioService;
	private ITaskService taskService;
	
	public IScenarioService getScenarioService() {
		return scenarioService;
	}
	@Resource
	public void setScenarioService(IScenarioService scenarioService) {
		this.scenarioService = scenarioService;
	}

	public ITaskService getTaskService() {
		return taskService;
	}
	@Resource
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping("/show")
	public String show(HttpSession session, Model model){
		List<TaskModel> scenTaskList = new ArrayList<TaskModel>();
		List<TaskModel> mobileTaskList = new ArrayList<TaskModel>();
		List<TaskModel> caseTaskList = new ArrayList<TaskModel>();
		List<TaskModel> interfaceCaseList = new ArrayList<TaskModel>();
		User u = (User) session.getAttribute("loginUser");
		for(TaskModel t : taskService.getTaskModelListByUid(u.getId())){
			switch(t.getTaskType()){
				case WEB_SCENARIO : scenTaskList.add(t);break;
				case WEB_CASE : caseTaskList.add(t);break;
				case MOBILE_CASE:mobileTaskList.add(t);break;
				case INTERFACE_CASE : interfaceCaseList.add(t);break;
				default:;
			}
		}
		model.addAttribute("scenarios", scenarioService.getScenarioBeanList(u.getId()));
		model.addAttribute("scenariotasks", scenTaskList);
		model.addAttribute("casetasks", caseTaskList);
		model.addAttribute("interfacetasks", interfaceCaseList);
		model.addAttribute("mobiletasks",mobileTaskList);
		return "tree/tree";
	}
}
