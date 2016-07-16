package org.phoenix.api;

import org.phoenix.api.action.IInterfaceAPI;
import org.phoenix.checkpoint.ICheckPoint;
import org.phoenix.mobile.android.action.IAndroidAppAPI;
import org.phoenix.mobile.ios.action.IIOSAppAPI;
import org.phoenix.plugins.IFtpClient;
import org.phoenix.plugins.IImageReader;
import org.phoenix.plugins.ISvnClient;
import org.phoenix.plugins.ITelnetClient;
/**
 * 统一代理接口
 * @author mengfeiyang
 *
 */
public interface IProxy {
	/**
	 * IOS操作API
	 * @return
	 */
	IIOSAppAPI iosAPI();
	/**
	 * Android操作API
	 * @return
	 */
	IAndroidAppAPI androidAPI();
	/**
	 * 接口测试API
	 * @return
	 */
	IInterfaceAPI interfaceAPI();
	/**
	 * 获取通用方法
	 * @return
	 */
	ICommonAPI commonAPI();
	/**
	 * web页面操作API
	 * @return
	 */
	IWebAPI webAPI();
	/**
	 * 获取检查点代理方法
	 * @return
	 */
	ICheckPoint checkPoint();
	/**
	 * socket客户端，用于对socket服务器做操作
	 * @return
	 */
	ITelnetClient telnetClient();
	/**
	 * svn客户端，用于对svn做操作。如获取提交日志，提交文件，更新文件等等。
	 * @return
	 */
	ISvnClient svnClient();
	/**
	 * 图片解析，用于识别图片上的字符。可直接对网络图片或本地图片进行读取
	 * @return
	 */
	IImageReader imageReader();
	/**
	 * ftp客户端，用于操作ftp服务器，如从ftp服务器下载文件和上传本地文件到服务器等操作。
	 * @return
	 */
	IFtpClient ftpClient();
}
