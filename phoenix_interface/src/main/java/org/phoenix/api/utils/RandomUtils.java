package org.phoenix.api.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机字符工具类
 * @author mengfeiyang
 *
 */
public class RandomUtils {
	
	/**
	 * 产生随机的字符串
	 * @param len 字符串长度
	 * @return
	 */
	public static String getRanCHS(int len) {
		String ret = "";
		for (int i = 0; i < len; i++) {
			String str = null;
			int hightPos, lowPos; // 定义高低位
			Random random = new Random();
			hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
			lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
			byte[] b = new byte[2];
			b[0] = (new Integer(hightPos).byteValue());
			b[1] = (new Integer(lowPos).byteValue());
			try {
				str = new String(b, "GBK"); // 转成中文
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			ret += str;
		}
		return ret;
	}
	
	/**
	 * 随机产生指定数个的小写英文字符
	 * @param len
	 * @return
	 */
	public static String getRanEN(int len) {
		String str = "";
		for (int i = 0; i < len; i++) {
			str = str + (char) (Math.random() * 26 + 'a');
		}
		return str;
	}
	
	/**
	 * 产生一个0-num之间的数字，不保证位数.如果想取负数，请在方法前加 -
	 * @param num
	 * @return
	 */
	public static int getRanInt(int num){
		return ThreadLocalRandom.current().nextInt(num);
	}
	
	/**
	 * 产生一个0-num之间的数字，且保证位数为length，如果想取负数，请在方法前加 -
	 * @param num
	 * @param length
	 * @return
	 */
	public static int getRanInt(int num,int length){
		int newInt = getRanInt(num);
		int olen = Integer.toString(newInt).length();
		while(olen < length){
			newInt = getRanInt(num);
			olen = Integer.toString(newInt).length();
		}
		return newInt;
	}
	/**
	 * 随机产生一个小于size的float数据,如size=1000，则可能返回：291.58676，如果想取负数，请在方法前加 -
	 * @param size
	 * @return
	 */
	public static float getRanFloat(int size){
		return ThreadLocalRandom.current().nextFloat()*size;
	}
	
	/**
	 * 随机产生一个小于size的double数据，如size=1000，则可能返回：901.3634319184971.如果想取负数，请在方法前加 -
	 * @param size
	 * @return
	 */
	public static double getRanDouble(int size){
		return ThreadLocalRandom.current().nextDouble()*size;
	}
	
	/**
	 * 产生一个随机的long数据.如果想取负数，请在方法前加 -
	 * @return
	 */
	public static long getRanLong(){
		return ThreadLocalRandom.current().nextLong();
	}
	
	/**
	 * 在给定种子的列表中随机产生指定长度的字符串，如种子为：String oos = {"a","b","1","2"};产生随机字符时，从oos中获取
	 * @param len
	 * @param seed
	 * @return
	 */
	public static String getRanString(int len,String[] seed){
		StringBuilder result = new StringBuilder();
		for(int i=0;i<len;i++){
			int index = ThreadLocalRandom.current().nextInt(seed.length);
			result.append(seed[index]);
		}
		return result.toString();
	}
}
