package org.phoenix.node.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.phoenix.node.dto.AjaxObj;

public class WriteResponse {

	public static void writeXml(HttpServletResponse response, String content) {
		try {
	        response.setContentType("text/html");  
	        response.setCharacterEncoding("utf-8");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setHeader("Cache-Control", "no-cache, must-revalidate");  
	        response.setHeader("Pragma", "no-cache"); 
			PrintWriter out = response.getWriter();

			out.println(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeXml(HttpServletResponse response, AjaxObj ajaxObj) {
		try {
	        response.setContentType("text/html");  
	        response.setCharacterEncoding("utf-8");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setHeader("Cache-Control", "no-cache, must-revalidate");  
	        response.setHeader("Pragma", "no-cache"); 
			PrintWriter out = response.getWriter();

			out.println(ajaxObj);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
