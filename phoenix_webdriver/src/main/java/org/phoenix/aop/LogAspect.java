package org.phoenix.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LogAspect {
	
	/*
	 * 第一个*为任意返回值
	 * 第二个*为这个包中的所有类
	 * 第三个*为以add开头的所有方法
	 * (..)表示任意参数
	 */
    public void logStart(JoinPoint jp){
		//System.out.println(jp.getTarget()); //获取执行类
		System.out.println(jp.getSignature().getName()); //获取执行的方法
    	PhoenixLogger.info(jp.getSignature().getName() + " 操作开始执行");
    }
	@After("execution(* org.phoenix.action.WebElementAction.*(..))")
    public void logEnd(JoinPoint jp){ //获取执行的方法
    	System.out.println("------");
    	PhoenixLogger.info(jp.getSignature().getName() + " 操作执行成功");
    }
	
/*	@Around("execution(* org.mfy.dao.*.add*(..))||"
			+ "execution(* org.mfy.dao.*.dele*(..))||"
			+ "execution(* org.mfy.dao.*.update*(..))")*/
    public void logAround(ProceedingJoinPoint pj) throws Throwable{
    	PhoenixLogger.info("方法调用之前加入日志");
    	pj.proceed();//执行方法
    	PhoenixLogger.info("方法调用之后加入日志");
    }
	
}
