package org.phoenix.node.reflect;

import java.lang.reflect.Field;

/**
 * 反射操作属性值
 * @author M.F.Yang
 *
 */

public class ReflectOperateAttribute {
	static Class<?> Clazz = null;
	static Object obj = null;
	static String attribValue;
   /**
    * 反射获取已存在的属性值
    * @author M.F.Yang
    * @param classPath
    * @param attributeValue
    * @return
    */
	public static String getAttributeValue(String classPath, String attributeValue){
		try{
			Clazz = Class.forName(classPath);
		obj = Clazz.newInstance();
		Field field = Clazz.getDeclaredField(attributeValue);
		field.setAccessible(true);
		attribValue = (String) field.get(attributeValue);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 	attribValue ;
	}
	/**
	 * 反射设置属性值
	 * @author M.F.Yang
	 * @param classPath
	 * @param attributeName
	 * @param attributeValue
	 */
	
	public static void setAttributeValue(String classPath,String attributeName,String attributeValue){
		try{		
			Clazz = Class.forName(classPath);
		obj = Clazz.newInstance();
		
		Field field = Clazz.getDeclaredField(attributeName);
		field.setAccessible(true);
		field.set(obj, attributeValue);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
}
