package org.phoenix.node.action;

import java.lang.reflect.Method;
import java.util.LinkedList;

import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.node.compiler.DynamicEngine;
import org.phoenix.utils.MethodPattern;

/**
 * 执行用例的入口
 * @author mengfeiyang
 *
 */
public class ExecuteMethod {
	
	/**
	 * 动态编译并执行用例代码
	 * @param codeContent
	 * @param caseLogBean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<UnitLogBean> runByJavaCode(String codeContent,CaseLogBean caseLogBean) throws Exception{		
		//"public\\sclass(.*)(?=\\{)").split("\\{")[0]
		String packageName = MethodPattern.result(codeContent, "package(.*);").trim();
		String className = MethodPattern.result(codeContent, "public\\sclass\\s(.*)extends\\sWebElementActionProxy").trim();
        DynamicEngine de = DynamicEngine.getInstance();
        Class<?> clazz =  de.javaCodeToClass(packageName+"."+className,codeContent);
    	Method method = clazz.getDeclaredMethod("run",CaseLogBean.class);
    	LinkedList<UnitLogBean> unitLogs = (LinkedList<UnitLogBean>) method.invoke(clazz.newInstance(),caseLogBean); 
    	
    	return unitLogs;	    
	}
}
