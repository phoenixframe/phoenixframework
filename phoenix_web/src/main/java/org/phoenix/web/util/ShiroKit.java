package org.phoenix.web.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroKit {
	
	public static String md5(String password,String salt) {
		String p = null;
		p = new Md5Hash(password, salt).toHex();
		return p;
	}
	
	public static String base64Encode(String password){
		return Base64.encodeToString(password.getBytes());
	}
	public static String base64Decode(String password){
		return Base64.decodeToString(password);
	}
	
	public static boolean isEmpty(Object obj) {
		
		if(obj instanceof String) {
			return "".equals(obj);
		}
		
		if(obj instanceof Integer) {
			return (Integer)obj==0;
		}
		if(obj==null) return true;
		else return false;
	}
}
