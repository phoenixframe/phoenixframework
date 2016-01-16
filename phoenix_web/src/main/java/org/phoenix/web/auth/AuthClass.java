package org.phoenix.web.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 只要在Controller上增加了这个方法的类，都需要进行权限的控制
 * @author Administrator
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthClass {
	/*
	 * 如果value为admin就表示这个类只能超级管理员访问
	 * 如果value为login表示这个类中的方法，某些可能为相应的角色可以访问
	 */
	public String value() default "admin";
}
