package org.phoenix.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.phoenix.model.ScenarioBean;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.dto.ScenarioDTO;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IScenarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 场景控制器
 * @author mengfeiyang
 *
 */
@Controller
@RequestMapping("/scenario")
@AuthClass("login")
public class ScenarioController {
	private IScenarioService scenarioService;
	
	public IScenarioService getScenarioService() {
		return scenarioService;
	}
    @Resource
	public void setScenarioService(IScenarioService scenarioService) {
		this.scenarioService = scenarioService;
	}

	public ScenarioController() {
	}
	
	@RequestMapping("/list")
	public String list(Model model,HttpSession httpSession){
		User u = (User)httpSession.getAttribute("loginUser");
		model.addAttribute("datas", scenarioService.getScenarioBeanPager(u.getId()));
		return "scenario/list";
	}
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public String select(String keyWord,Model model,HttpSession httpSession){
		User u = (User)httpSession.getAttribute("loginUser");
		model.addAttribute("keyWord",keyWord);
		model.addAttribute("datas", scenarioService.getScenarioBeanPager(u.getId(), keyWord));
		return "scenario/list";
	}
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		scenarioService.delete(id);
		return "redirect:/scenario/list";
	}
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute(new ScenarioDTO());
		return "scenario/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid ScenarioDTO scenarioDTO,BindingResult br,	HttpSession httpSession){
		if(br.hasErrors()){
			return "scenario/add";
		}
		User u = (User)httpSession.getAttribute("loginUser");
		ScenarioBean scenarioBean = new ScenarioBean();
		scenarioBean.setCreateDate(new Date());
		scenarioBean.setRemark(scenarioDTO.getRemark());
		scenarioBean.setScenarioName(scenarioDTO.getScenarioName());
		scenarioBean.setUserId(u.getId());
		scenarioService.add(scenarioBean);
		return "redirect:/scenario/list";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model){
		model.addAttribute("scenarioBean", scenarioService.getScenarioBean(id));
		model.addAttribute("scenarioDTO", new ScenarioDTO());
		return "scenario/edit";
	}
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid ScenarioDTO scenarioDTO,BindingResult br,Model model){
	    if(br.hasErrors()){
	    	return "scenario/edit";
	    }
	    ScenarioBean scenarioBean = scenarioService.getScenarioBean(id);
	    scenarioBean.setRemark(scenarioDTO.getRemark());
	    scenarioBean.setScenarioName(scenarioDTO.getScenarioName());
	    scenarioService.update(scenarioBean);
		return "redirect:/scenario/list";
	}
}
