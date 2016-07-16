package org.phoenix.api.utils;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * json解析工具类
 * @author mengfeiyang
 *
 */
public class JsonPaser {
	private static HashMap<String,String> hm = new HashMap<String,String>();
	
	/**
	 * 递归获取每个JSONObject的最后一个节点的key与value<br>
	 * 如果遇到相等的key，则在后一个key值拼接随机种子<br>
	 * <em>开发日期：2014-7-10 16:41</em>
	 * @author mengfeiyang
	 * @param obj
	 * @throws Exception
	 */
	public static void getJSONode(Object obj) throws Exception{
		if(obj instanceof JSONObject){						
			JSONObject jo = (JSONObject) obj;
			String[] names = JSONObject.getNames(jo);
			for(String na : names){
				try {
					 getJSONode(jo.getJSONObject(na));	
				} catch (JSONException e) {	
                     if(jo.get(na) instanceof JSONArray){ 
                    	 if(!jo.get(na).toString().contains(":")){
                    		 hm.put(na, jo.get(na).toString());
                    	 }else{
                    		 getJSONode(jo.get(na));
                    	 }                   	
					}else{						
						if (hm.containsKey(na)) {
							//String k = na + new Random().nextInt();
							hm.put(na+"=>"+jo.getString(na), jo.getString(na));
						}else{
							hm.put(na, jo.getString(na));
						}
					}                    
				}
			}			
		}else if(obj instanceof JSONArray){
			JSONArray jarr = (JSONArray)obj;
			for(int i=0;i<jarr.length();i++){
					JSONObject jso = jarr.getJSONObject(i);
					getJSONode(jso);
			}	
		}
	}
	
	/**
	 * 对节点进行解析
	 * 
	 * @author mengfeiyang
	 * @param obj
	 * @param node
	 * @return
	 */
	private static JSONObject getObj(JSONObject obj, String node) {
		try {
			if (node.contains("[")) {
				JSONArray arr = obj.getJSONArray(node.substring(0,node.indexOf("[")));
				for (int i = 0; i < arr.length(); i++) {
					if ((i + "").equals(node.substring(node.indexOf("["),node.indexOf("]")).replace("[", ""))) {
						return arr.getJSONObject(i);
					}
				}
			} else {
				return obj.getJSONObject(node);
			}
		} catch (Exception e) {
			return obj;
		}
		return null;
	}
	
	/**
	 * 获取节点值
	 * @author mengfeiyang
	 * @param jsonContent
	 * @param jsonPath
	 * @return
	 * @throws Exception
	 */
	public static synchronized String getNodeValue(String jsonContent, String jsonPath) throws Exception {
		String[] nodes = jsonPath.split("\\.");
		JSONObject obj = new JSONObject(jsonContent);

		for (int i = 1; i < nodes.length; i++) {
			if (obj != null) {
				obj = getObj(obj, nodes[i]);
			}

			if ((i + 1) == nodes.length) {
				try{
					return obj.getString(nodes[i]);
				}catch(Exception e){
					return "JSONException:"+e.getMessage()+",NodeString:"+obj.toString();
				}
			}
		}
		return null;
	}
}
