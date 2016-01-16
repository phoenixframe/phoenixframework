package org.phoenix.node.reflect;

import java.lang.reflect.Method;

/**
 * 反射执行指定类中的指定的方法
 * @author M.F.Yang
 *
 */

public class ReflectExcuMethod {	
	private String resultInfo;
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
	public ReflectExcuMethod(String classPath,String methodName){
		Class<?> clazz = null;
		
		try{
			clazz = Class.forName(classPath);
			Method method = clazz.getMethod(methodName);
			method.invoke(clazz.newInstance());
			setResultInfo(methodName+" 成功执行完成！");
		}catch(Exception e){
			e.printStackTrace();
			setResultInfo(methodName+" 执行失败！异常信息："+e);
		}
	}

}
