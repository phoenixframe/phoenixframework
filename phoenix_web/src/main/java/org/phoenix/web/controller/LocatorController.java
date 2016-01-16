package org.phoenix.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.phoenix.enums.LocatorType;
import org.phoenix.model.CaseBean;
import org.phoenix.model.LocatorBean;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.dto.LocatorDTO;
import org.phoenix.web.service.ICaseService;
import org.phoenix.web.service.ILocatorService;
import org.phoenix.web.util.EnumUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/locator")
@AuthClass("login")
public class LocatorController {
	private ILocatorService locatorService;
	private ICaseService caseService;

	public ICaseService getCaseService() {
		return caseService;
	}
	@Resource
	public void setCaseService(ICaseService caseService) {
		this.caseService = caseService;
	}
	public ILocatorService getLocatorService() {
		return locatorService;
	}
    @Resource
	public void setLocatorService(ILocatorService locatorService) {
		this.locatorService = locatorService;
	}

	public LocatorController() {
	}
	
	@RequestMapping("/case/{id}")
	public String list(@PathVariable Integer id,Model model){
		model.addAttribute("caseBean", caseService.getCaseBean(id));
		model.addAttribute("datas",locatorService.getLoatorBeanPager(id));
		return "locator/list";
	}
	
	@RequestMapping(value="/batchadd/{id}",method=RequestMethod.GET)
	public String batchAdd(@PathVariable Integer id,Model model){
		model.addAttribute("caseBean", caseService.getCaseBean(id));
		model.addAttribute("types", EnumUtils.enumProp2NameMap(LocatorType.class, "name"));
		model.addAttribute("locatorDTO", new LocatorDTO());
		return "locator/batchadd";
	}
	@RequestMapping(value="/batchadd/{id}",method=RequestMethod.POST)
	public String batchAdd(String caseId,LocatorDTO locatorDTO){
		List<LocatorBean> newlocatorBeans = new ArrayList<LocatorBean>();
		List<LocatorBean> ll = locatorDTO.getLocatorBeanList();
		for(LocatorBean l : ll){
			if(l.getLocatorDataName() != null && !l.getLocatorDataName().equals("")){
				l.setLocatorData(l.getLocatorData().replace("\\", ""));
				newlocatorBeans.add(l);
			}
		}
		locatorService.addLocatorBeanList(newlocatorBeans);
		return "redirect:/locator/case/"+caseId;
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id){
		LocatorBean locatorBean = locatorService.getLocatorBean(id);
		locatorService.delLocator(id);
		return "redirect:/locator/case/"+locatorBean.getCaseBean().getId();
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id,Model model){
		model.addAttribute("types", EnumUtils.enumProp2NameMap(LocatorType.class, "name"));
		model.addAttribute(locatorService.getLocatorBean(id));
		model.addAttribute(new LocatorDTO());
		return "locator/edit";
	}
	
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id,@Valid LocatorDTO locatorDTO,BindingResult br,Model model){
		if(br.hasErrors()){
			return "locator/edit";
		}
		CaseBean caseBean = new CaseBean();
		caseBean.setId(locatorDTO.getCaseId());
		
		LocatorBean locatorBean = locatorService.getLocatorBean(id);
		locatorBean.setCaseBean(caseBean);
		locatorBean.setLocatorData(locatorDTO.getLocatorData().replace("\\", ""));
		locatorBean.setLocatorDataName(locatorDTO.getLocatorDataName());
		locatorBean.setLocatorType(locatorDTO.getLocatorType());
		locatorService.updateLocator(locatorBean);
		return "redirect:/locator/case/"+locatorDTO.getCaseId();
	}
	
	
}
