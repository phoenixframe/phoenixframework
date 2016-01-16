package org.phoenix.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.phoenix.enums.MsgStatusType;
import org.phoenix.model.MsgBean;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.email.EmailSender;
import org.phoenix.web.model.TaskModel;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IMsgService;
import org.phoenix.web.service.ITaskService;
import org.phoenix.web.util.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/msg")
@AuthClass("login")
public class MsgController {
	private IMsgService msgService;
	private ITaskService taskService;

	public ITaskService getTaskService() {
		return taskService;
	}
	@Resource
	public void setTaskService(ITaskService taskService) {
		this.taskService = taskService;
	}
	public IMsgService getMsgService() {
		return msgService;
	}
	@Resource
	public void setMsgService(IMsgService msgService) {
		this.msgService = msgService;
	}
	
	@RequestMapping("/list")
	public String list(Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("msgStatusTypes", EnumUtils.enumProp2NameMap(MsgStatusType.class, "name"));
		model.addAttribute("datas", msgService.getMsgBeanPager(u.getId()));
		
		return "msg/list";
	}
	@RequestMapping("/select")
	public String select(String msgStatusType,Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("msgStatus", msgStatusType);
		model.addAttribute("msgStatusTypes", EnumUtils.enumProp2NameMap(MsgStatusType.class, "name"));
		model.addAttribute("datas", msgService.getMsgBeanPagerByStatus(msgStatusType,u.getId()));
		return "msg/list";
	}
	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Integer id,Model model){
		MsgBean msgBean = msgService.getMsgBean(id);
		EmailSender emailSender = new EmailSender();
		
		TaskModel taskModel = taskService.getTaskModel(msgBean.getTaskModelId());
		emailSender.send();
		model.addAttribute(msgBean);
		model.addAttribute(taskModel);
		return "msg/detail";
	}
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		msgService.deleteMsg(id);
		return "redirect:/msg/list";
	}
	
}
