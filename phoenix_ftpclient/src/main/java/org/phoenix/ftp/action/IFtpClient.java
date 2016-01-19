package org.phoenix.ftp.action;

/**
 * phoenixframe的ftp操作模块，可对ftp服务器做文件的上传和下载
 * @author mengfeiyang
 *
 */
public interface IFtpClient {
	/**
	 * 重要信息配置，并初始化FTPClient
	 * @param serverIp
	 * @param serverport
	 * @param loginName
	 * @param password
	 * @param ftpServerFolder 服务器设置的存储根目录是： F:\FTPServer,如果想将文件保存在： F:\FTPServer\report\,则需要将此字段填为： /report/
	 */
	IFtpClient ftpClientConfig(String serverIp,int serverport,String loginName,String password,String ftpServerFolder);
	/**
	 * 通过FTP上传一个文件到服务器指定目录<br>
	 * 通过Log4j打印跟踪日志<br>
	 * <em>编写日期：2014年2月14日 15:25</em>
	 * @author mengfeiyang
	 * @version 1.0
	 * @since JDK 1.7 及以上
	 *
	 */
	String ftpUploadFile(String filePath);
    /**
     * 通过FTP服务器指定目录下载一个文件到本地指定目录,
     * 只需指定FTPServer的指定文件名即可
     * <em>编写日期：2014年2月14日 15:25</em>
     * @author mengfeiyang
     * @version 1.0
     * @since JDK 1.7 及以上
     *
     */
	String ftpFileDownLoad(String FileName,String localPath);
	
	/**
	 * 断开与ftp服务器的连接
	 */
	void ftpDisconnect();
}
