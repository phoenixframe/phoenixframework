package org.phoenix.web.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("rawtypes")
public class EnumUtils {
	/*
	 * 将枚举中的值转换为一组序数列表
	 */
	public static List<Integer> enum2Ordinal(Class<? extends Enum> clz) {
		if(!clz.isEnum()) return null;
		Enum[] enums = clz.getEnumConstants();
		List<Integer> rels = new ArrayList<Integer>();
		for(Enum en:enums) {
			rels.add(en.ordinal());
		}
		return rels;
	}
	/*
	 * 将枚举中的值转换为相应的名称字符串列表
	 */
	public static List<String> enum2Name(Class<? extends Enum> clz) {
		if(!clz.isEnum()) return null;
		Enum[] enums = clz.getEnumConstants();
		List<String> rels = new ArrayList<String>();
		for(Enum en:enums) {
			rels.add(en.name());
		}
		return rels;
	}
	/*
	 * 将枚举中的值转换为序号和名称的map
	 */
	public static Map<Integer,String> enum2BasicMap(Class<? extends Enum> clz) {
		if(!clz.isEnum()) return null;
		Enum[] enums = clz.getEnumConstants();
		Map<Integer,String> rels = new HashMap<Integer,String>();
		for(Enum en:enums) {
			rels.put(en.ordinal(),en.name());
		}
		return rels;
	}
	/*
	 * 将枚举中的值的某个属性转换为字符串列表
	 */
	public static List<String> enumProp2List(Class<? extends Enum> clz,String propName) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			List<String> rels = new ArrayList<String>();
			for(Enum en:enums) {
				rels.add((String)PropertyUtils.getProperty(en, propName));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 将枚举中的值的某个属性转换为序号和字符串列表
	 */
	public static Map<Integer,String> enumProp2OrdinalMap(Class<? extends Enum> clz,String propName) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			Map<Integer,String> rels = new HashMap<Integer,String>();
			for(Enum en:enums) {
				rels.put(en.ordinal(),(String)PropertyUtils.getProperty(en, propName));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 将枚举中的值的某个属性转换为名称和字符串map
	 */
	public static Map<String,String> enumProp2NameMap(Class<? extends Enum> clz,String propName) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			Map<String,String> rels = new HashMap<String,String>();
			for(Enum en:enums) {
				rels.put(en.name(),(String)PropertyUtils.getProperty(en, propName));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 将枚举中的两个属性转换为map
	 */
	public static Map<String,String> enumProp2Map(Class<? extends Enum> clz,String keyProp,String valueProp) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			Map<String,String> rels = new HashMap<String,String>();
			for(Enum en:enums) {
				rels.put((String)PropertyUtils.getProperty(en,keyProp),(String)PropertyUtils.getProperty(en,valueProp));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
