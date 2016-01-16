package org.phoenix.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.phoenix.enums.MsgSendType;
import org.phoenix.model.CaseBean;
import org.phoenix.model.ScenarioBean;
import org.phoenix.utils.MethodPattern;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.dto.CaseDTO;
import org.phoenix.web.enums.TaskType;
import org.phoenix.web.model.User;
import org.phoenix.web.service.ICaseService;
import org.phoenix.web.service.IScenarioService;
import org.phoenix.web.util.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/case")
@AuthClass("login") 
public class CaseController {
	
	private ICaseService caseService;
    private CaseDTO caseDTO;
    private IScenarioService scenarioService;

	public CaseDTO getCaseDTO() {
		return caseDTO;
	}

	public void setCaseDTO(CaseDTO caseDTO) {
		this.caseDTO = caseDTO;
	}

	public ICaseService getCaseService() {
		return caseService;
	}

	@Resource
	public void setCaseService(ICaseService caseService) {
		this.caseService = caseService;
	}
	
	public IScenarioService getScenarioService() {
		return scenarioService;
	}
    @Resource
	public void setScenarioService(IScenarioService scenarioService) {
		this.scenarioService = scenarioService;
	}

	public CaseController() {
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String listByScenario(@PathVariable Integer id,Model model){
		model.addAttribute("scenId", id);
		model.addAttribute("datas",caseService.getCaseBeanPagerByScenario(id));
		return "case/list";
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute("datas",caseService.getCaseBeanPagerByUser(u.getId()));
		return "case/list";
	}
	@RequestMapping(value="/select",method=RequestMethod.GET)
	public String select(String keyWord,String keyWord2,Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("keyWord2", keyWord2);
		model.addAttribute("datas", caseService.getCaseBeanPagerByKeyWord(u.getId(), keyWord, keyWord2));
		return "case/list";
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delCase(@PathVariable int id){
		caseService.delCase(id);
		
		return "redirect:/case/list";
	}
	
	@RequestMapping(value="/add/{id}",method=RequestMethod.GET)
	public String addByScenario(@PathVariable Integer id,Model model){
		model.addAttribute("scenId", id);
		model.addAttribute("caseDTO", new CaseDTO());
		return "case/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Integer scenId,Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute("msgSendTypes",EnumUtils.enumProp2NameMap(MsgSendType.class, "name"));
		model.addAttribute("caseTypes",EnumUtils.enumProp2NameMap(TaskType.class, "name"));
		model.addAttribute("sceanList", scenarioService.getScenarioBeanList(u.getId()));
		model.addAttribute("caseDTO", new CaseDTO());
		return "case/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(Integer scenId,@Valid CaseDTO caseDTO ,BindingResult br,Model model,HttpSession httpSession){
		if(br.hasErrors()){
			return "case/add";
		}
		ScenarioBean scenaBean = new ScenarioBean();
		scenaBean.setId(scenId);
		CaseBean caseBean = new CaseBean();
		caseBean.setCreateDate(new Date());
		caseBean.setScenarioBean(scenaBean);
		User user = (User)httpSession.getAttribute("loginUser");
		caseBean.setUserId(user.getId());
		caseBean.setCaseName(caseDTO.getCaseName());
		caseBean.setRemark(caseDTO.getRemark());
		caseBean.setStatus(caseDTO.getStatus());
		caseBean.setDeleteMsg(caseDTO.isDeleteMsg());
		caseBean.setCaseType(caseDTO.getCaseType());
		caseBean.setMsgSendType(caseDTO.getMsgSendType());
		
		caseService.addCase(caseBean);
		return "redirect:/case/list";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model,HttpSession session){
		User u = (User) session.getAttribute("loginUser");
		model.addAttribute("msgSendTypes",EnumUtils.enumProp2NameMap(MsgSendType.class, "name"));
		model.addAttribute(caseService.getCaseBean(id));
		model.addAttribute("scenList", scenarioService.getScenarioBeanList(u.getId()));
		model.addAttribute(new CaseDTO());
		return "case/edit";
	}
	
	@RequestMapping(value="/update/editor",method=RequestMethod.POST)
	public String update(@Valid CaseDTO caseDTO,BindingResult br,Model model){
		if(br.hasErrors()){
			return "/case/edit";
		}
		ScenarioBean scenBean = new ScenarioBean();
		scenBean.setId(caseDTO.getScenId());
		String className = "";
		try{
			className = MethodPattern.result(caseDTO.getCodeContent(), "public\\sclass\\s(.*)extends\\sWebElementActionProxy").trim();
		}catch(NullPointerException e){}
		CaseBean caseBean = caseService.getCaseBean(caseDTO.getId());
		caseBean.setCaseName(caseDTO.getCaseName());
		caseBean.setCodeContent(caseDTO.getCodeContent());
		caseBean.setRemark(caseDTO.getRemark());
		caseBean.setStatus(caseDTO.getStatus());
		caseBean.setScenarioBean(scenBean);
		caseBean.setClassName(className);
		caseBean.setDeleteMsg(caseDTO.isDeleteMsg());
		caseBean.setMsgSendType(caseDTO.getMsgSendType());
		
		caseService.updateCase(caseBean);
		
		return "redirect:/case/list";
	}
	
}
