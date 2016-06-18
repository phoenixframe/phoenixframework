package org.phoenix.web.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.phoenix.enums.TaskStatusType;
import org.phoenix.web.dto.AjaxObj;
import org.phoenix.web.dto.TaskModelDTO;
import org.phoenix.web.enums.JobStatus;
import org.phoenix.web.enums.TaskType;
import org.phoenix.web.filter.InitServlet;
import org.phoenix.web.model.SlaveModel;
import org.phoenix.web.model.TaskModel;
import org.phoenix.web.model.User;
import org.phoenix.web.quartz.JobFactory;
import org.phoenix.web.quartz.TaskHandler;
import org.phoenix.web.service.ICaseService;
import org.phoenix.web.service.IScenarioService;
import org.phoenix.web.service.ISlaveService;
import org.phoenix.web.service.ITaskService;
import org.phoenix.web.util.EnumUtils;
import org.phoenix.web.util.HttpRequestSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/task")
public class TaskController {
	private ITaskService taskService;
	private ISlaveService slaveService;
	private ICaseService caseService;
	private IScenarioService scenarioService;
	
	public IScenarioService getScenarioService() {
		return scenarioService;
	}
	@Resource
	public void setScenarioService(IScenarioService scenarioService) {
		this.scenarioService = scenarioService;
	}
	public ICaseService getCaseService() {
		return caseService;
	}
	@Resource
	public void setCaseService(ICaseService caseService) {
		this.caseService = caseService;
	}
	public ITaskService getTaskService() {
		return taskService;
	}
    @Resource
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}

	public ISlaveService getSlaveService() {
		return slaveService;
	}
	@Resource
	public void setSlaveService(ISlaveService slaveService) {
		this.slaveService = slaveService;
	}
	public TaskController() {
	}
	
	
	@RequestMapping("/list")
	public String list(Model model,HttpSession httpSession){
		User u = (User)httpSession.getAttribute("loginUser");
		model.addAttribute("types", EnumUtils.enumProp2NameMap(TaskType.class, "name"));
		model.addAttribute("status", EnumUtils.enumProp2NameMap(TaskStatusType.class, "name"));
		model.addAttribute("jobs", EnumUtils.enumProp2NameMap(JobStatus.class, "name"));
		model.addAttribute("datas",taskService.getTaskModelPagerByUser(u.getId()));
		
		return "task/list";
	}
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String select(String type,String tstatus,String jobStatus,Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("types", EnumUtils.enumProp2NameMap(TaskType.class, "name"));
		model.addAttribute("status", EnumUtils.enumProp2NameMap(TaskStatusType.class, "name"));
		model.addAttribute("jobs", EnumUtils.enumProp2NameMap(JobStatus.class, "name"));
		model.addAttribute("tstatus", tstatus);
		model.addAttribute("type", type);
		model.addAttribute("jobStatus", jobStatus);
		model.addAttribute("datas", taskService.getTaskModelPagerBySelect(u.getId(), tstatus, type,jobStatus));
		return "task/list";
	}
	@RequestMapping("/strategyList")
	public String strategyList(){
		
		return "task/strategy";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("types", EnumUtils.enumProp2NameMap(TaskType.class, "name"));
		model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
		model.addAttribute(new TaskModelDTO());
		return "task/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid TaskModelDTO taskModelDTO,BindingResult br,HttpSession httpSession,Model model){
		if(br.hasErrors()){
			User u = (User)httpSession.getAttribute("loginUser");
			model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
			return "task/add";
		}
		SlaveModel slaveModel = new SlaveModel();
		slaveModel.setId(taskModelDTO.getSlaveId());
		User user = (User)httpSession.getAttribute("loginUser");
		TaskModel taskModel = new TaskModel();
		taskModel.setUser(user);
		taskModel.setSlaveModel(slaveModel);
		taskModel.setTaskName(taskModelDTO.getTaskName());
		taskModel.setTaskParameter(taskModelDTO.getTaskParameter());
		taskModel.setTaskStatusType(TaskStatusType.NOT_RUNNING);
		taskModel.setTaskType(taskModelDTO.getTaskType());
		taskModel.setTaskData(taskModelDTO.getTaskData().split("_")[0]);
		taskModel.setBeanName(taskModelDTO.getTaskData().split("_")[1]);
		TaskModel newTask = taskService.add(taskModel);
		if(taskModelDTO.getTaskParameter().trim().equals("")){
			taskModel.setJobStatus(JobStatus.NOT_JOB);
		} else {
			taskModel.setJobStatus(JobStatus.STOP);
		}
		taskService.update(newTask);
		return "redirect:/task/list";
	}
	
	@RequestMapping("/startJob/{id}")
	public String startJob(@PathVariable Integer id){
		TaskModel taskModel = taskService.getTaskModel(id);
		String r = JobFactory.addJob(TaskHandler.class, taskModel);
		if(r.equals("success")){
			taskModel.setJobStatus(JobStatus.RUNNING);
		} else {
			taskModel.setJobStatus(JobStatus.ERROR);
			taskModel.setMessage(r);
		}
		taskService.update(taskModel);
		
		return "redirect:/task/list";
	}
	
	@RequestMapping("/stopJob/{id}")
	public String stopJob(@PathVariable Integer id){
		TaskModel taskModel = taskService.getTaskModel(id);
		String r = JobFactory.deleteJob(taskModel);
		if(r.equals("success")){
			taskModel.setJobStatus(JobStatus.STOP);
		} else {
			taskModel.setJobStatus(JobStatus.ERROR);
			taskModel.setMessage(r);
		}
		taskService.update(taskModel);
		return "redirect:/task/list";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		JobFactory.deleteJob(taskService.getTaskModel(id));
		taskService.delete(id);
		return "redirect:/task/list";
	}
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model,HttpSession session){
		User user = (User)session.getAttribute("loginUser");
		TaskModel taskModel = taskService.getTaskModel(id);
		TaskType taskType = taskModel.getTaskType();
		model.addAttribute("types", EnumUtils.enumProp2NameMap(TaskType.class, "name"));
		model.addAttribute("slaves", slaveService.getSlaveModelList(user.getId()));
		model.addAttribute("taskModel",taskModel);
		model.addAttribute("taskModelDTO",new TaskModelDTO());
		if("WEB_CASE".equals(taskType.getName()))model.addAttribute("beanList", caseService.getCaseBeanListByUser(user.getId()));
		if("MOBILE_CASE".equals(taskType.getName()))model.addAttribute("beanList", caseService.getCaseBeanListByUT(user.getId(),"MOBILE_CASE"));
		if("INTERFACE_CASE".equals(taskType.getName()))model.addAttribute("beanList", caseService.getCaseBeanListByUT(user.getId(),"INTERFACE_CASE"));
		if("WEB_SCENARIO".equals(taskType.getName()))model.addAttribute("beanList", scenarioService.getScenarioBeanList(user.getId()));

		return "task/edit";
	}
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid TaskModelDTO taskModelDTO,BindingResult br,Model model,HttpSession session){
		if(br.hasErrors()){
			User u = (User)session.getAttribute("loginUser");
			model.addAttribute("slaves", slaveService.getSlaveModelList(u.getId()));
			return "task/edit";
		}
		SlaveModel slaveModel = slaveService.getModel(taskModelDTO.getSlaveId());
		TaskModel taskModelSrc = taskService.getTaskModel(id);
		taskModelSrc.setSlaveModel(slaveModel);
		taskModelSrc.setTaskName(taskModelDTO.getTaskName());
		taskModelSrc.setTaskParameter(taskModelDTO.getTaskParameter());
		taskModelSrc.setTaskStatusType(taskModelDTO.getTaskStatusType());
		taskModelSrc.setTaskType(taskModelDTO.getTaskType());
		taskModelSrc.setJobStatus(JobStatus.WAITING);
		taskModelSrc.setTaskStatusType(TaskStatusType.NOT_RUNNING);
		taskModelSrc.setTaskData(taskModelDTO.getTaskData().split("_")[0]);
		taskModelSrc.setBeanName(taskModelDTO.getTaskData().split("_")[1]);
		if(taskModelDTO.getTaskParameter().trim().equals("")){
			taskModelSrc.setJobStatus(JobStatus.NOT_JOB);
		} else {
			String r = JobFactory.updateJob(taskModelSrc);
			if(r.equals("success")){
				taskModelSrc.setJobStatus(JobStatus.RUNNING);
			}else{
				taskModelSrc.setJobStatus(JobStatus.ERROR);
				taskModelSrc.setMessage(r);
			}
		}
		taskService.update(taskModelSrc);
		return "redirect:/task/list";
	}
	
	@RequestMapping(value="/start/{id}",method=RequestMethod.POST,produces="text/plain;charset=UTF-8")
	public @ResponseBody String start(@PathVariable Integer id){
		TaskModel taskModel = taskService.getTaskModel(id);
		String hostIP = taskModel.getSlaveModel().getSlaveIP();
		int taskId = taskModel.getId();
		
		String url = "http://"+hostIP+"/phoenix_node/action.do?taskId="+taskId+"&taskType="+taskModel.getTaskType();
		try {
			return HttpRequestSender.getResponseByPost(url);
		} catch (Exception e) {
			return JSON.toJSONString(new AjaxObj(0,"向执行机分配测试任务时发生异常，信息："+e.getMessage()));
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/stop/{id}")
	public @ResponseBody AjaxObj stop(@PathVariable Integer id){
        HashMap<String,String> hm = (HashMap<String, String>) InitServlet.getWc().getBean("constants");
        System.out.println(hm.toString());
		
		return new AjaxObj(1);
	}
}
