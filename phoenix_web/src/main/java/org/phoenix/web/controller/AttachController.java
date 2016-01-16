package org.phoenix.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.model.AttachModel;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IAttachService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用于对附件进行增删改操作，上传的附件保存在服务器目录下，可在
 * 控制台“我的附件”中查看到路径，此路径可用于需要上传附件的测试类型
 * @author mengfeiyang
 * 
 */
@AuthClass("login")
@Controller
@RequestMapping("/attach")
public class AttachController {
	private IAttachService attachService;
	
	public IAttachService getAttachService() {
		return attachService;
	}
	@Resource
	public void setAttachService(IAttachService attachService) {
		this.attachService = attachService;
	}

	@RequestMapping("/list")
	public String list(Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("datas", attachService.getAttachPager(u.getId()));
		return "attach/list";
	}
	@RequestMapping(value="/select",method=RequestMethod.POST)
	public String select(String keyWord,Model model,HttpSession session){
		User u = (User)session.getAttribute("loginUser");
		model.addAttribute("keyWord", keyWord);
		model.addAttribute("datas", attachService.getAttachPagerByKeyWord(u.getId(), keyWord));
		return "attach/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(){
		return "attach/add";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@RequestParam("attachs")MultipartFile[] attachs,HttpServletRequest req) throws IOException{
		String realpath = req.getSession().getServletContext().getRealPath("/resources/upload");
		User u = (User)req.getSession().getAttribute("loginUser");
		for(MultipartFile attach:attachs) {
			if(attach.isEmpty()) continue;
			File f = new File(realpath+"/"+attach.getOriginalFilename());
			FileUtils.copyInputStreamToFile(attach.getInputStream(),f);
			attachService.add(new AttachModel(attach.getOriginalFilename(),req.getRequestURL().toString().replace(req.getServletPath(), "")+"/resources/upload/"+attach.getOriginalFilename(),attach.getContentType(),new Date(),u));
		}
		return "redirect:/attach/list";
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable Integer id,HttpSession session){
		AttachModel attachModel = attachService.getAttachModel(id);
		String fileRealPath = session.getServletContext().getRealPath("")+"/resources/upload/"+attachModel.getAttachName();
		new File(fileRealPath).delete();
		attachService.deleteAttach(id);
		return "redirect:/attach/list";
	}
}
