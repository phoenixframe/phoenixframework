package org.phoenix.node.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象与String之间的相互转换
 * @author mengfeiyang
 *
 */
public class ObjectOper {  
   
	/**
	 * 将实现了 Serializable 接口的对象转换成String
	 * @param obj
	 * @return
	 * @throws Exception
	 */
    public static String Obj2String(Serializable obj) throws Exception {  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);  
        objectOutputStream.writeObject(obj);    
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");  
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
          
        objectOutputStream.close();  
        byteArrayOutputStream.close();  
           
        return serStr;
    }  
    
    /**
     * 将String转换成对象
     * @param str
     * @return
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Object String2Obj(String str) throws ClassNotFoundException, IOException{
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");  
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));  
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);   
        Object dataObj = (Object)objectInputStream.readObject();   
        objectInputStream.close();  
        byteArrayInputStream.close();  
        return dataObj;
    }
    
    /**
     * 将已放在List中的实现了Serializable接口的对象写入到文件中
     * @param obj
     * @param fileName
     * @throws IOException
     */
    public static void Obj2File(List<?> obj,String fileName) throws IOException{
		File file = new File(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		for(Object o : obj){
			oos.writeObject(o);
		}		
		oos.close();
		fos.close();
    }
    
    /**
     * 从指定的文件中读取对象，返回类型：List<Object>
     * @param fileName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Object> File2Obj(String fileName) throws IOException, ClassNotFoundException{
    	FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
        List<Object> objList = new ArrayList<Object>();
		while(true){
			try{
				objList.add(ois.readObject());
			}catch(EOFException e){
				break;
			}catch(NullPointerException ee){
				continue;
			}
		}
		ois.close();
		fis.close();
		
		return objList;
    } 
    
    /**
     * 对象转byte[]
     * @param obj
     * @return
     */
    public static byte[] objct2Bytes(java.lang.Object obj) {
        if(obj == null){
            return null;
        }else{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream ops = new ObjectOutputStream(
                        byteArrayOutputStream);
                ops.writeObject(obj);
                byte[] byteA = byteArrayOutputStream.toByteArray();
                return byteA;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * byte[]转数组
     * @param byteA
     * @return
     */
    public static java.lang.Object bytes2Objct(byte[] byteA) {
        InputStream in = new ByteArrayInputStream(byteA);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * blob转Object
     * @param blob
     * @return
     */
    public static Object blobToObjct(Blob blob) {
        ObjectInputStream ois = null;
        try {
            InputStream in = blob.getBinaryStream();
            ois = new ObjectInputStream(in);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}  