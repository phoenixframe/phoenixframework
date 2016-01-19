package org.phoenix.ftp.action;

import org.phoenix.ftp.ftpclient.PhoenixFtpClient;

/**
 * 对ftp服务器上的文件做上传和下载
 * @author mengfeiyang
 *
 */
public class FtpClient implements IFtpClient{
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.ftp.action.IFtpClient#ftpClientConfig(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IFtpClient ftpClientConfig(String serverIp, int serverport,
			String loginName, String password, String ftpServerFolder) {
		PhoenixFtpClient.config(serverIp, serverport, loginName, password, ftpServerFolder);
		return this;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.ftp.action.IFtpClient#ftpUploadFile(java.lang.String)
	 */
	@Override
	public String ftpUploadFile(String filePath) {
		return PhoenixFtpClient.FTPUpload(filePath);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.ftp.action.IFtpClient#ftpFileDownLoad(java.lang.String, java.lang.String)
	 */
	@Override
	public String ftpFileDownLoad(String FileName, String localPath) {
		return PhoenixFtpClient.FTPDownload(FileName, localPath);
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.ftp.action.IFtpClient#disconnect()
	 */
	@Override
	public void ftpDisconnect() {
		PhoenixFtpClient.disconnect();		
	}
}
