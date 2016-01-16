package org.phoenix.basic.paging;

import javax.servlet.http.HttpServletRequest;


/**
 * 在service层获取应用层中的核心数据
 * @author mengfeiyang
 *
 */
public class SystemRequest {
	private HttpServletRequest request;
	private int pageSize;
	private int pageOffset;
	private String sort;
	private String order;
	private String realpath;
	
	
	public String getRealpath() {
		if(request!=null) {
			realpath = request.getSession().getServletContext().getRealPath("");
		}
		return realpath;
	}

	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}

	public int getPageSize() {
		return (pageSize<=0)?15:pageSize;
	}
	
	public int getPageOffset() {
		return (pageOffset<=0)?0:pageOffset;
	}
	
	public String getSort() {
		return sort;
	}
	
	public String getOrder() {
		return order;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}


	public void setSort(String sort) {
		this.sort = sort;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
