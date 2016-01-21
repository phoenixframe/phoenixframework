package org.phoenix.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.utils.ScreenShot;
import org.phoenix.utils.SystemInfo;

import com.google.common.collect.Lists;

/**
 * @author mengfeiyang
 * 使用动态代理需要实现InvocationHandler接口
 * 
 */
public class ActionInvocationHandler implements InvocationHandler {
	private Object target = null;
	private LinkedList<UnitLogBean> unitLog;
	private CaseLogBean caseLogBean;
	private List<String> otherOpers = Lists.newArrayList("appElement","webElement","webAPIAction","checkPoint","toString","getData");

	public ActionInvocationHandler(Object target,LinkedList<UnitLogBean> unitLog,CaseLogBean caseLogBean) {
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
			if(returnObj!=null && !returnObj.equals("null"))returnObj = returnObj.toString().length()>200?returnObj.toString().substring(0, 200)+"...":returnObj.toString();
			if(!otherOpers.contains(method.getName())){
				unitLog.add(new UnitLogBean("步骤 [ "+method.getName()+" ]执行成功，参数值："+Arrays.toString(args)+",执行结果返回值："+returnObj,method.getName(),"STEP","SUCCESS","",caseLogBean));
				PhoenixLogger.info("步骤 [ "+method.getName()+" ]执行成功，参数值："+Arrays.toString(args)+",执行结果返回值："+returnObj);
			}
		}catch(Exception e){
			String picPath = null;
			String picWebPath = null;
			if(SystemInfo.isWindows()){
				long picName = new Date().getTime();
				picPath = ScreenShot.TakeScreenshot(caseLogBean.getAttachPath()+"/screenshot/"+picName+".jpg");
				picWebPath = "<a href='http://"+caseLogBean.getClientIP()+"/phoenix_node/screenshot/"+picName+".jpg' target='_blank'>点击查看</a>";
			} else {
				picPath = picWebPath = "Linux系统下不支持截图";
			}
			unitLog.add(new UnitLogBean("步骤 [ "+method.getName()+" ]执行失败，参数值："+Arrays.toString(args)+",异常信息："+e.getClass().getSimpleName()+",msg:"+e.getMessage()+",caused by:"+e.getCause().toString(),method.getName(),"STEP","FAIL",picWebPath,caseLogBean));
			PhoenixLogger.info("步骤 [ "+method.getName()+" ]执行失败，参数值："+Arrays.toString(args)+",异常信息："+e.getClass().getSimpleName()+",msg:"+e.getMessage()+",caused by:"+e.getCause().toString()+",截图路径："+picPath);
		}
		return result;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(), this);
	}
}