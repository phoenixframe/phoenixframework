package org.phoenix.web.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.phoenix.web.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 用户登录控制器
 * @author mengfeiyang
 *
 */
@Controller
public class LoginController {
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}
	
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "public/login"; 
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,Model model) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			return "redirect:/index";
		} catch (AuthenticationException e) {
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			model.addAttribute("emsg", e.getMessage());
			return "public/login";
		}
	}
	
	@RequestMapping("/logout")
	public void logout() {
		SecurityUtils.getSubject().logout();
	}
}
