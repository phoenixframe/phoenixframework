package org.phoenix.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;

import com.beust.jcommander.internal.Lists;

/**
 * @author mengfeiyang
 * 使用动态代理需要实现InvocationHandler接口
 * 
 */
public class WebApiInvocationHandler implements InvocationHandler {
	private Object target = null;
	private LinkedList<UnitLogBean> unitLog;
	private CaseLogBean caseLogBean;
	private List<String> list = Lists.newArrayList("equals","toString");

	public WebApiInvocationHandler(Object target,LinkedList<UnitLogBean> unitLog,CaseLogBean caseLogBean) {
		this.target = target;
		this.unitLog = unitLog;
		this.caseLogBean = caseLogBean;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		Object result = null;
		Object returnObj = null;
		try{
			result = method.invoke(this.target, args);
			returnObj = result;
			if(returnObj!=null && !returnObj.equals("null"))returnObj = returnObj.toString().length()>100?returnObj.toString().substring(0, 100)+"...":returnObj.toString();
			if(!list.contains(method.getName())){
				unitLog.add(new UnitLogBean("接口方法 ["+method.getName()+"] 执行通过，相关参数："+Arrays.toString(args),method.getName(),"STEP","SUCCESS","",caseLogBean));
				PhoenixLogger.info("接口方法 ["+method.getName()+"] 执行通过，相关参数："+Arrays.toString(args)+"，返回结果值："+returnObj);
			}
		}catch(Exception e){
			if(!list.contains(method.getName())){
				unitLog.add(new UnitLogBean("接口方法 ["+method.getName()+"] 执行失败，相关参数："+Arrays.toString(args)+",异常信息："+e.getClass().getSimpleName()+",msg:"+e.getMessage()+",caused by:"+e.getCause().toString(),method.getName(),"STEP","FAIL","",caseLogBean));
				PhoenixLogger.error("接口方法 ["+method.getName()+"] 执行失败，相关参数："+Arrays.toString(args)+",异常信息："+e.getClass().getSimpleName()+",msg:"+e.getMessage()+",caused by:"+e.getCause().toString());
			}
		}
		return result;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(), this);
	}
}