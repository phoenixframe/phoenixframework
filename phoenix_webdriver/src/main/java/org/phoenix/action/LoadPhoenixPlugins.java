package org.phoenix.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.phoenix.aop.PhoenixLogger;

/**
 * 为已加载的插件生成实例
 * @author mengfeiyang
 *
 */
public class LoadPhoenixPlugins {
	private static HashMap<String,Object> plugins = new HashMap<String,Object>();
	
	public LoadPhoenixPlugins(){
		loadPlugin();
	}
	
	public static Object getPlugin(String name){
		return plugins.get(name);
	}
	
	private void loadPlugin(){
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(LoadPhoenixPlugins.class.getResource("/").getPath().replace("%20", " ")+"plugin.properties"));
			for(Entry<Object,Object> entry:prop.entrySet()){
				String value = (String)entry.getValue();
				String key = (String)entry.getKey();
				try {
					Object o = Class.forName(value).newInstance();
					plugins.put(key, o);
				} catch (Exception e) {
				}
			}
		} catch (FileNotFoundException e) {
			PhoenixLogger.error("请将平台的plugin配置文件 plugin.properties 放到工程src目录下."+e.getMessage());
		} catch (IOException e) {
			PhoenixLogger.error("请将平台的plugin配置文件 plugin.properties 放到工程src目录下且确认文件可被访问,"+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new LoadPhoenixPlugins();
		System.out.println(LoadPhoenixPlugins.getPlugin("SvnClient"));
	}
}
