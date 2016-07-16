package org.phoenix.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

public class ResourceCheckFilter extends AccessControlFilter {
	
	private String errorUrl;
	
	public String getErrorUrl() {
		return errorUrl;
	}

	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		String url = getPathWithinApplication(request);
		//System.out.println("filter:"+url);
		return subject.isPermitted(url);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		HttpServletResponse hsp = (HttpServletResponse)response;
		HttpServletRequest hReq = (HttpServletRequest)request;
		hsp.sendRedirect(hReq.getContextPath()+"/"+this.errorUrl);
		return false;
	}

}
