package org.phoenix.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 1、创建一个代理类，并实现InvocationHandler接口
 * @author mengfeiyang
 *
 */
public class LoggerProxy implements InvocationHandler{
	
	private LoggerProxy(){}
	/**
	 * 2、创建一个代理对象
	 */
	private Object target;
	

	/**
	 * 3、创建一个方法来生成对象，这个方法的参数是要代理的对象,getInstance所返回的对象就是代理对象
	 * @param o
	 * @return
	 */
	public static Object getInstance(Object o){

		//3.1创建LoggerProxy对象
		LoggerProxy proxy = new LoggerProxy();
		//3.2设置这个代理对象
		proxy.target = o;
		/**
		 * 3.3 通过proxy的方法创建代理对象，
		 * 第一个参数是要代理的classLoader
		 * 第二个参数是要代理对象实现的所有接口，
		 * 第三个参数是要实现类InvocationHandler的对象
		 * 此时的result就是一个代理对象，代理的是 o
		 */
		Object result = Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), proxy);
	    return result;
	}

	/**
	 * 当有了代理对象以后，不管这个代理对象执行什么方法，都会调用以下的invoke方法
	 */
	@Override
	public Object invoke(Object object, Method method, Object[] args)
			throws Throwable {
		//对指定的方法添加log信息
/*		if(method.getName().equals("add") || method.getName().equals("dele")){
		   Logger.info("进行了相应的操作");
		}*/
		
		//放在 58行 前，代表方法执行前打印log信息。也可以放在 58行 之后，代表方法执行完成后打印log信息
		if(method.isAnnotationPresent(LogInfo.class)){
			LogInfo li = method.getAnnotation(LogInfo.class);
			PhoenixLogger.info(li.value());
		}
		Object obj = method.invoke(target, args);
		System.out.println(method + "执行成功");
		return obj;
	}

}
