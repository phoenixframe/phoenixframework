package org.phoenix.web.dto;

import java.util.List;

public class ChartDTO {
	private String title;
	private String subTitle;
	private String[] xdata;
	private String ydata;
	private String toolTip = "";
	private List<ChartDataDTO> chartDataList;
	
	public ChartDTO(String title, String subTitle, String ydata, String toolTip) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.ydata = ydata;
		this.toolTip = toolTip;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String[] getXdata() {
		return xdata;
	}
	public void setXdata(String[] xdata) {
		this.xdata = xdata;
	}
	
	public String getYdata() {
		return ydata;
	}
	public void setYdata(String ydata) {
		this.ydata = ydata;
	}
	public List<ChartDataDTO> getChartDataList() {
		return chartDataList;
	}
	public void setChartDataList(List<ChartDataDTO> chartDataList) {
		this.chartDataList = chartDataList;
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
}
