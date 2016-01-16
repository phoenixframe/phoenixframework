package org.phoenix.web.filter;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.phoenix.web.aop.PhoenixLogger;
import org.phoenix.web.auth.AuthUtil;
import org.phoenix.web.quartz.InitTask;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 初始化spring配置
 * @author mengfeiyang
 *
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static WebApplicationContext wc;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		new PhoenixLogger();
		wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		Map<String,Set<String>> auths = AuthUtil.initAuth("org.phoenix.web.controller");
		this.getServletContext().setAttribute("allAuths", auths);
		new InitTask().init();
		System.out.println("------------------------系统初始化成功:"+auths+"-----------------------------");
	}
	
	public static WebApplicationContext getWc() {
		return wc;
	}
	
	public static Object getBean(String name) {
		return wc.getBean(name);
	}
}
