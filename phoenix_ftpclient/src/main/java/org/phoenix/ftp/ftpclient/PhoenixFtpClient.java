package org.phoenix.ftp.ftpclient;

import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 

import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileOutputStream; 

/**
 * 通过FTP上传一个文件到服务器指定目录
 * <em>编写日期：2014年2月14日 15:25</em>
 * @author mengfeiyang
 * @version 1.0
 * @since JDK 1.7 及以上
 *
 */
public class PhoenixFtpClient { 
	private static String serverIp;
	private static int serverport;
	private static String loginName;
	private static String password;
	private static String ftpServerFolder;
	private static FTPClient ftpClient;
	private static FileOutputStream fos = null;
	private static FileInputStream fis = null;
	
	/**
	 * 重要信息配置，并初始化FTPClient
	 * @param serverIp
	 * @param serverport
	 * @param loginName
	 * @param password
	 * @param ftpServerFolder 服务器设置的存储根目录是： F:\FTPServer,如果想将文件保存在： F:\FTPServer\report\,则需要将此字段填为： /report/
	 */
	public static void config(String serverIp,int serverport,String loginName,String password,String ftpServerFolder){
		PhoenixFtpClient.serverIp = serverIp;
		PhoenixFtpClient.serverport = serverport;
		PhoenixFtpClient.loginName = loginName;
		PhoenixFtpClient.password = password;
		PhoenixFtpClient.ftpServerFolder = ftpServerFolder;
        ftpClient = new FTPClient(); 
	}
	

	/**
	 * 通过FTP上传一个文件到服务器指定目录<br>
	 * 通过Log4j打印跟踪日志<br>
	 * <em>编写日期：2014年2月14日 15:25</em>
	 * @author mengfeiyang
	 * @version 1.0
	 * @since JDK 1.7 及以上
	 *
	 */
    public static String FTPUpload(String FilePath) { 
        try { 
        	String uploadResult;
            ftpClient.connect(serverIp,serverport); 
            ftpClient.login(loginName, password); 
            System.out.println(ftpClient.getReplyString());
            File srcFile = new File(FilePath); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            ftpClient.changeWorkingDirectory(ftpServerFolder); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            
            String[] FilePaths = FilePath.split("\\\\");
            String fileName = FilePaths[FilePaths.length-1];

            ftpClient.storeFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"),fis);// 转换后的目录名或文件名。, fis);  
            if(ftpClient.getReplyString().contains("complete")){
            	uploadResult = "上传文件："+fileName+" 到FTP服务器成功";
                System.out.println(ftpClient.getReplyString());
            }else{
            	uploadResult = ftpClient.getReplyString();
            	System.out.println(ftpClient.getReplyString());
            }
            return uploadResult;
        } catch (Exception e) { 
        	return "上传文件 到FTP服务器失败！异常信息："+e.getMessage();
        } 
    } 

    /**
     * 通过FTP服务器指定目录下载一个文件到本地指定目录,
     * 只需指定FTPServer的指定文件名即可
     * <em>编写日期：2014年2月14日 15:25</em>
     * @author mengfeiyang
     * @version 1.0
     * @since JDK 1.7 及以上
     *
     */
    public static String FTPDownload(String FileName,String localPath) {  
        try { 
            ftpClient.connect(serverIp,serverport); 
            ftpClient.login(loginName, password);
            System.out.println(ftpClient.getReplyString());
            String remoteFileName = ftpServerFolder+FileName; 
            fos = new FileOutputStream(localPath+File.pathSeparator+FileName); 

            ftpClient.setBufferSize(1024); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.retrieveFile(remoteFileName, fos); 
            System.out.println(ftpClient.getReplyString());
            return "从FTP服务器下载文件："+FileName+" 到本地成功";
        } catch (Exception e) { 
        	return "从FTP服务器下载文件失败！异常信息："+e.getMessage();
        }
    }
    
    public static void disconnect(){
        try { 
            IOUtils.closeQuietly(fos); 
            IOUtils.closeQuietly(fis); 
            ftpClient.disconnect(); 
            System.out.println(ftpClient.getReplyString());
        } catch (Exception e) { 
        } 
    }
} 
