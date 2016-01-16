package org.phoenix.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.phoenix.web.dto.AjaxObj;
import org.phoenix.web.model.SlaveModel;
import org.phoenix.web.model.User;
import org.phoenix.web.service.ISlaveService;
import org.phoenix.web.util.HttpRequestSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meterware.httpunit.WebResponse;

/**
 * 节点控制器
 * @author mengfeiyang
 *
 */
@Controller
@RequestMapping("/slave")
public class SlaveController {
	private ISlaveService slaveService;

	public ISlaveService getSlaveService() {
		return slaveService;
	}
    @Resource
	public void setSlaveService(ISlaveService slaveService) {
		this.slaveService = slaveService;
	}
    
    @RequestMapping("/list")
    public String list(Model model,HttpSession session){
    	User u = (User)session.getAttribute("loginUser");
    	model.addAttribute("datas",slaveService.getSlaveModelPager(u.getId()));
    	return "slave/list";
    }
    
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
    	model.addAttribute(new SlaveModel());
    	return "slave/add";
    }
    
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@Valid SlaveModel slaveModel,BindingResult br,HttpSession session){
    	if(br.hasErrors()){
    		return "slave/add";
    	}
    	User u = (User)session.getAttribute("loginUser");
    	slaveModel.setUid(u.getId());
    	slaveService.add(slaveModel);
    	return "redirect:/slave/list";
    }
    
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
    	slaveService.delete(id);
    	return "redirect:/slave/list";
    }
    @RequestMapping(value="/update/{id}",method=RequestMethod.GET)
    public String update(@PathVariable Integer id,Model model){
    	model.addAttribute(slaveService.getModel(id));
    	return "slave/edit";
    }
    
    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(@PathVariable Integer id,@Valid SlaveModel slaveModel,BindingResult br){
    	if(br.hasErrors()){
    		return "slave/edit";
    	}
    	SlaveModel slaveModelSrc = slaveService.getModel(id);
    	slaveModelSrc.setRemark(slaveModel.getRemark());
    	slaveModelSrc.setSlaveIP(slaveModel.getSlaveIP());
    	slaveService.update(slaveModelSrc);
    	return "redirect:/slave/list";
    }
    @RequestMapping("/status")
    public String status(Model model,HttpSession session){
    	WebResponse webResponse = null;
    	User u = (User)session.getAttribute("loginUser");
    	List<SlaveModel> slaveList = slaveService.getSlaveModelList(u.getId());
    	  for(SlaveModel s : slaveList){
    		  String url = "http://"+s.getSlaveIP()+"/phoenix_node/action.do?requestType=getStatus";
    		  try {
    			  webResponse = HttpRequestSender.getResponseObjectByGet(url);
    			  AjaxObj ajaxObj = JSON.toJavaObject(JSONObject.parseObject(webResponse.getText()), AjaxObj.class);
    			  s.setStatus(webResponse.getResponseCode()+","+ajaxObj.getMsg());
				} catch (Exception e) {
					s.setStatus("状态异常："+e.getMessage());
				}
    		  slaveService.update(s);
    	 }
    	  model.addAttribute("datas", slaveList);
    	
    	return "slave/status";
    }
}
