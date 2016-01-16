package org.phoenix.api.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * Base64,MD5,DES,AES加密算法
 * 
 * @author mengfeiyang
 *
 */
@SuppressWarnings("restriction")
public class SecurityUtil {

	public static String Md5Encode16(String s) {
		byte[] utfBytes = s.getBytes();
		StringBuffer buf = new StringBuffer();
		String md5Str = null;
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(utfBytes);

			byte b[] = mdTemp.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			md5Str = buf.toString().substring(8, 24);

			return md5Str;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String Md5Encode32(String s) {
		byte[] utfBytes = s.getBytes();
		MessageDigest mdTemp;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(utfBytes);

			String md5String = new BigInteger(1, mdTemp.digest()).toString(16);

			return md5String;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String Base64Encode(String s) {
		byte[] sourceStr = s.getBytes();
		BASE64Encoder b64Encoder = new BASE64Encoder();
		String encodeStr = b64Encoder.encode(sourceStr);
		return encodeStr;
	}

	public static String DesEnc(String data, String key) {
		byte[] bt = DesEncode(data.getBytes(), key.getBytes());
		String strs = null;

		try {
			strs = URLEncoder.encode(new BASE64Encoder().encode(bt), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strs;
	}

	private static byte[] DesEncode(byte[] datasource, byte[] password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String AesEnc(String data, String key) {

		String strs = null;
		try {
			byte[] bt = AesEncrypt(data, key);
			strs = URLEncoder.encode(new BASE64Encoder().encode(bt), "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strs;

	}

	public static byte[] AesEncrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");// "算法/模式/补码方式"
		// IvParameterSpec iv = new
		// IvParameterSpec("abcdefghijklmnopqrstuvwxyz0123456789".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		// cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());

		return encrypted;
	}

}
