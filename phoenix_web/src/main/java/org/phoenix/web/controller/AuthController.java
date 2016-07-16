package org.phoenix.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.phoenix.web.auth.AuthClass;
import org.phoenix.web.auth.AuthMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 根据不同的用户权限返回不同的菜单树
 * @author mengfeiyang
 *
 */
@Controller
@AuthClass("login")
@RequestMapping("/auth")
public class AuthController {
	
	/*
	 * 权限管理的临时替代方案,不过此种方法较简单且易维护
	 */
	@AuthMethod
	@RequestMapping(value="/menu",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAuth(HttpSession session) {
    	String MENU_ADMIN = "["+
    	        "{"+
    	    	        "\"id\": \"1\","+
    	    	        "\"homePage\": \"1\","+
    	    	        "\"menu\": ["+
    	    	            "{"+
    	    	                "\"text\": \"系统管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"1\","+
    	    	                        "\"text\": \"版本说明\","+
    	    	                        "\"href\": \"index/welcome\""+
    	    	                    "},"+
    	    	                    "{"+
		    	                        "\"id\": \"2\","+
		    	                        "\"text\": \"用户管理\","+
		    	                        "\"href\": \"user/list\""+
	    	                         "},"+
	    	                         "{"+
		    	                        "\"id\": \"3\","+
		    	                        "\"text\": \"我的信息\","+
		    	                        "\"href\": \"user/self\""+
    	                             "}"+
    	    	                "]"+
    	    	            "}"+
    	    	        "]"+
    	    	    "},"+
    	    	    "{"+
    	    	        "\"id\": \"2\","+
    	    	        "\"homePage\": \"1\","+
    	    	        "\"menu\": ["+
    	    	            "{"+
    	    	                "\"text\": \"业务管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"1\","+
    	    	                        "\"text\": \"场景管理\","+
    	    	                        "\"href\": \"scenario/list\""+
    	    	                    "},"+
    	    	                    "{"+
		    	                        "\"id\": \"2\","+
		    	                        "\"text\": \"用例管理\","+
		    	                        "\"href\": \"case/list\""+
	    	                         "},"+
	    	    	                 "{"+
		    	                        "\"id\": \"12\","+
		    	                        "\"text\": \"业务树形图\","+
		    	                        "\"href\": \"tree/show\""+
	    	                         "}"+
    	    	                "]"+
    	    	            "},"+
    	    	            "{"+
    	    	                "\"text\": \"任务管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"3\","+
    	    	                        "\"text\": \"任务分配\","+
    	    	                        "\"href\": \"task/list\""+
    	    	                    "},"+
/*    	    	                    "{"+
		    	                        "\"id\": \"4\","+
		    	                        "\"text\": \"任务监控\","+
		    	                        "\"href\": \"task/list\""+
	    	                        "},"+*/
    	    	                    "{"+
		    	                        "\"id\": \"5\","+
		    	                        "\"text\": \"执行机状态\","+
		    	                        "\"href\": \"slave/status\""+
	    	                        "},"+ 
		    	                    "{"+
		    	                        "\"id\": \"6\","+
		    	                        "\"text\": \"执行机管理\","+
		    	                        "\"href\": \"slave/list\""+
		    	                    "}"+
    	    	                "]"+
    	    	            "},"+
    	    	            "{"+
	    	                "\"text\": \"性能测试\","+
	    	                "\"items\": ["+
	    	                    "{"+
	    	                        "\"id\": \"13\","+
	    	                        "\"text\": \"场景配置\","+
	    	                        "\"href\": \"perf/list\""+
	    	                    "}"+
/*    	    	                ",{"+
	    	                        "\"id\": \"14\","+
	    	                        "\"text\": \"历史结果\","+
	    	                        "\"href\": \"perf/history\""+
    	                        "}"+*/
	    	                  "]"+
	    	                "},"+
    	    	            "{"+
    	    	                "\"text\": \"日志管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"7\","+
    	    	                        "\"text\": \"日志查看\","+
    	    	                        "\"href\": \"log/batchlist\""+
    	    	                    "},"+
    	    	                    "{"+
		    	                        "\"id\": \"8\","+
		    	                        "\"text\": \"用例统计图\","+
		    	                        "\"href\": \"chart/CASE\""+
	    	                        "},"+
    	    	                    "{"+
		    	                        "\"id\": \"9\","+
		    	                        "\"text\": \"场景统计图\","+
		    	                        "\"href\": \"chart/SCENARIO\""+
	    	                        "}"+
    	    	                "]"+
    	    	            "},"+
    	    	            "{"+
		    	               "\"text\": \"消息池管理\","+
		    	               "\"items\": ["+
		    	                      "{"+
		    	                        "\"id\": \"10\","+
		    	                        "\"text\": \"消息列表\","+
		    	                        "\"href\": \"msg/list\""+
		    	                      "}"+
		    	                   "]"+
	    	                "},"+
    	    	            "{"+
		    	               "\"text\": \"附件管理\","+
		    	               "\"items\": ["+
		    	                      "{"+
		    	                        "\"id\": \"11\","+
		    	                        "\"text\": \"我的附件\","+
		    	                        "\"href\": \"attach/list\""+
		    	                      "}"+
		    	                   "]"+
	    	                "}"+
    	    	        "]"+
    	    	    "}"+
    	    	"]";
    	String MENU_USER = "["+
    	        "{"+
    	    	        "\"id\": \"1\","+
    	    	        "\"homePage\": \"1\","+
    	    	        "\"menu\": ["+
    	    	            "{"+
    	    	                "\"text\": \"系统管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"1\","+
    	    	                        "\"text\": \"版本说明\","+
    	    	                        "\"href\": \"index/welcome\""+
    	    	                    "},"+
	    	                         "{"+
		    	                        "\"id\": \"2\","+
		    	                        "\"text\": \"我的信息\","+
		    	                        "\"href\": \"user/self\""+
    	                             "}"+
    	    	                "]"+
    	    	            "}"+
    	    	        "]"+
    	    	    "},"+
    	    	    "{"+
    	    	        "\"id\": \"2\","+
    	    	        "\"homePage\": \"1\","+
    	    	        "\"menu\": ["+
    	    	            "{"+
    	    	                "\"text\": \"业务管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"1\","+
    	    	                        "\"text\": \"场景管理\","+
    	    	                        "\"href\": \"scenario/list\""+
    	    	                    "},"+
    	    	                    "{"+
		    	                        "\"id\": \"2\","+
		    	                        "\"text\": \"用例管理\","+
		    	                        "\"href\": \"case/list\""+
	    	                         "},"+
	    	    	                 "{"+
		    	                        "\"id\": \"12\","+
		    	                        "\"text\": \"业务树形图\","+
		    	                        "\"href\": \"tree/show\""+
	    	                         "}"+
    	    	                "]"+
    	    	            "},"+
    	    	            "{"+
    	    	                "\"text\": \"任务管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"3\","+
    	    	                        "\"text\": \"任务分配\","+
    	    	                        "\"href\": \"task/list\""+
    	    	                    "},"+
/*    	    	                    "{"+
		    	                        "\"id\": \"4\","+
		    	                        "\"text\": \"任务监控\","+
		    	                        "\"href\": \"task/list\""+
	    	                        "},"+*/
    	    	                    "{"+
		    	                        "\"id\": \"5\","+
		    	                        "\"text\": \"执行机状态\","+
		    	                        "\"href\": \"slave/status\""+
	    	                        "},"+  
	    	                        "{"+
		    	                        "\"id\": \"6\","+
		    	                        "\"text\": \"执行机管理\","+
		    	                        "\"href\": \"slave/list\""+
		    	                   "}"+
    	    	                "]"+
    	    	            "},"+
    	    	            "{"+
	    	                "\"text\": \"性能测试\","+
	    	                "\"items\": ["+
	    	                    "{"+
	    	                        "\"id\": \"13\","+
	    	                        "\"text\": \"场景配置\","+
	    	                        "\"href\": \"perf/list\""+
	    	                    "}"+
/*    	    	                ",{"+
	    	                        "\"id\": \"14\","+
	    	                        "\"text\": \"历史结果\","+
	    	                        "\"href\": \"perf/history\""+
    	                        "}"+*/
	    	                  "]"+
	    	                "},"+
    	    	            "{"+
    	    	                "\"text\": \"日志管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"7\","+
    	    	                        "\"text\": \"日志查看\","+
    	    	                        "\"href\": \"log/batchlist\""+
    	    	                    "},"+
    	    	                    "{"+
		    	                        "\"id\": \"8\","+
		    	                        "\"text\": \"用例统计图\","+
		    	                        "\"href\": \"chart/CASE\""+
	    	                        "},"+
    	    	                    "{"+
		    	                        "\"id\": \"9\","+
		    	                        "\"text\": \"场景统计图\","+
		    	                        "\"href\": \"chart/SCENARIO\""+
	    	                        "}"+
    	    	                "]"+
    	    	            "},"+
    	    	            "{"+
		    	               "\"text\": \"消息池管理\","+
		    	               "\"items\": ["+
		    	                      "{"+
		    	                        "\"id\": \"10\","+
		    	                        "\"text\": \"消息列表\","+
		    	                        "\"href\": \"msg/list\""+
		    	                      "}"+
		    	                   "]"+
	    	                "},"+
    	    	            "{"+
		    	               "\"text\": \"附件管理\","+
		    	               "\"items\": ["+
		    	                      "{"+
		    	                        "\"id\": \"11\","+
		    	                        "\"text\": \"我的附件\","+
		    	                        "\"href\": \"attach/list\""+
		    	                      "}"+
		    	                   "]"+
	    	                "}"+
    	    	        "]"+
    	    	    "}"+
    	    	"]";
    	String MENU_NORMAL = "["+
    	        "{"+
    	    	        "\"id\": \"1\","+
    	    	        "\"homePage\": \"1\","+
    	    	        "\"menu\": ["+
    	    	            "{"+
    	    	                "\"text\": \"系统管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"1\","+
    	    	                        "\"text\": \"版本说明\","+
    	    	                        "\"href\": \"index/welcome\""+
    	    	                    "}"+
    	    	                "]"+
    	    	            "}"+
    	    	        "]"+
    	    	    "},"+
    	    	    "{"+
    	    	        "\"id\": \"2\","+
    	    	        "\"homePage\": \"1\","+
    	    	        "\"menu\": ["+
    	    	            "{"+
    	    	                "\"text\": \"业务管理\","+
    	    	                "\"items\": ["+
    	    	                    "{"+
    	    	                        "\"id\": \"1\","+
    	    	                        "\"text\": \"版本说明\","+
    	    	                        "\"href\": \"index/welcome\""+
    	    	                    "}"+
    	    	                "]"+
    	    	            "}"+
    	    	        "]"+
    	    	    "}"+
    	    	"]";
    	
    	if(!SecurityUtils.getSubject().isAuthenticated()){
    		return MENU_NORMAL;
    	} else {
    		return SecurityUtils.getSubject().hasRole("0")?MENU_ADMIN:MENU_USER;
    	}
	}
}
