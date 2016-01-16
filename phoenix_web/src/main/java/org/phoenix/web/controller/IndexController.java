package org.phoenix.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	@RequestMapping({"/","/index"})
	public String index(Model model) {
		return "index/index";
	}
	@RequestMapping("index/welcome")
	public String welcome(){
		
		return "index/welcome";
	}
	
}
