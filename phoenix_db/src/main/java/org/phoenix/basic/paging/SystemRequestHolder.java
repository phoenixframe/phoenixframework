package org.phoenix.basic.paging;

/**
 * 请求上下文控制器
 * @author mengfeiyang
 *
 */
public class SystemRequestHolder {
	private final static ThreadLocal<SystemRequest> systemRequesthreadLocal = new ThreadLocal<SystemRequest>();
	
	public static void initRequestHolder(SystemRequest systemRequest) {
		systemRequesthreadLocal.set(systemRequest);
	}
	
	public static SystemRequest getSystemRequest() {
		return systemRequesthreadLocal.get();
	}
	
	public static void remove() {
		systemRequesthreadLocal.remove();
	}
}
