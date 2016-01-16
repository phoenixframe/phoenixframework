package org.phoenix.web.dto;

import java.util.ArrayList;
import java.util.List;

import org.phoenix.utils.GetNow;

import com.alibaba.fastjson.JSON;

public class ChartDataDTO {
	private String name;
	private double[] data;
	
	public ChartDataDTO(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double[] getData() {
		return data;
	}
	public void setData(double[] data) {
		this.data = data;
	}
	
	public static void main(String[] args) {
		List<ChartDataDTO> dataList = new ArrayList<ChartDataDTO>();
		
		ChartDataDTO chartData = new ChartDataDTO("测试数据");
		double[] strs = new double[]{10,2,30,15};
		chartData.setData(strs);
		
		ChartDataDTO chartData2 = new ChartDataDTO("测试数据2");
		double[] strs2 = new double[]{4,20,12,10};
		chartData2.setData(strs2);
		
		dataList.add(chartData);
		dataList.add(chartData2);
		
		ChartDTO chartDTO = new ChartDTO("响应时间统计","日期："+GetNow.getCurrentTime(),"响应时间（ms）","ms");
		chartDTO.setChartDataList(dataList);
		chartDTO.setXdata(new String[]{"1","2","3","4"});
		
		System.out.println(JSON.toJSONString(chartDTO));
	}
	
}
