package org.phoenix.node.reflect;

import java.lang.reflect.Field;

/**
 * 反射获取Page库中的所有已定义的Element
 * 
 * @author M.F.Yang
 * 
 */

public class ReflectGetElement {

	public ReflectGetElement() {

	}

	public static String[] getElements(String classPath) {
		String[] elements;
		Class<?> clazz;
		Field[] field = null;
		try {
			clazz = Class.forName(classPath);
			//Object obj = clazz.newInstance();
			field = clazz.getDeclaredFields();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		elements = new String[field.length];
		for (int i = 0; i < elements.length; i++) {
			elements[i] = field[i].getName();
		}
		return elements;
	}

}
