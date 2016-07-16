package org.phoenix.api.impl;

import java.util.LinkedList;

import org.phoenix.action.LoadPhoenixPlugins;
import org.phoenix.api.ICommonAPI;
import org.phoenix.api.IProxy;
import org.phoenix.api.IWebAPI;
import org.phoenix.api.action.IInterfaceAPI;
import org.phoenix.api.action.InterfaceAPI;
import org.phoenix.checkpoint.CheckPoint;
import org.phoenix.checkpoint.ICheckPoint;
import org.phoenix.mobile.android.action.IAndroidAppAPI;
import org.phoenix.mobile.ios.action.IIOSAppAPI;
import org.phoenix.model.CaseLogBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.plugins.IFtpClient;
import org.phoenix.plugins.IImageReader;
import org.phoenix.plugins.ISvnClient;
import org.phoenix.plugins.ITelnetClient;
import org.phoenix.proxy.CheckPointProxy;
import org.phoenix.proxy.CommonApiProxy;
import org.phoenix.proxy.WebAPIProxy;

/**
 * 代理类集合，通过此类，能直观的了解和使用平台的所有功能。
 * 后续功能扩展均在此类进行即可
 * @author mengfeiyang
 *
 */
public class ProxyAction implements IProxy{
	private IIOSAppAPI iosAppAPI;
	private IAndroidAppAPI androidAppAPI;
	private IWebAPI webAPI;
	private ICheckPoint checkPointAPI;
	private IInterfaceAPI interfaceAPI;
	private ICommonAPI commonAPI;
	
	public ProxyAction (LinkedList<UnitLogBean> unitLog,CaseLogBean caseLogBean){
		webAPI = (IWebAPI)new WebAPIProxy(new WebAPI(caseLogBean),unitLog,caseLogBean).getProxy();
		checkPointAPI = (ICheckPoint)new CheckPointProxy(new CheckPoint(),unitLog,caseLogBean).getProxy();
		iosAppAPI = (IIOSAppAPI)new CommonApiProxy("IOS",new IOSAppAPI(caseLogBean),unitLog,caseLogBean).getProxy();
		androidAppAPI = (IAndroidAppAPI)new CommonApiProxy("Android",new AndroidAppAPI(caseLogBean),unitLog,caseLogBean).getProxy();
		interfaceAPI = (IInterfaceAPI)new CommonApiProxy("接口",new InterfaceAPI(),unitLog,caseLogBean).getProxy();
		commonAPI = (ICommonAPI)new CommonApiProxy("通用",new CommonAPI(unitLog,caseLogBean),unitLog,caseLogBean).getProxy();
		
		webAPI.setWebProxy(webAPI);
		androidAppAPI.setAndroidAPIProxy(androidAppAPI);
	}
	/**
	 * IosAPI操作
	 */
	@Override
	public IIOSAppAPI iosAPI() {
		return iosAppAPI;
	}
	/**
	 * Android操作
	 */
	@Override
	public IAndroidAppAPI androidAPI() {
		return androidAppAPI;
	}
	/**
	 * web元素操作
	 */
	@Override
	public IWebAPI webAPI() {
		return webAPI;
	}
	/**
	 * 检查点操作
	 */
	@Override
	public ICheckPoint checkPoint() {
		return checkPointAPI;
	}
	/**
	 * socket工具类
	 */
	@Override
	public ITelnetClient telnetClient() {
		return (ITelnetClient) LoadPhoenixPlugins.getPlugin("TelnetClient");
	}
	/**
	 * svn工具类
	 */
	@Override
	public ISvnClient svnClient() {
		return (ISvnClient) LoadPhoenixPlugins.getPlugin("SvnClient");
	}
	/**
	 * 图像识别工具
	 */
	@Override
	public IImageReader imageReader() {
		return (IImageReader) LoadPhoenixPlugins.getPlugin("ImgReader");
	}
	/**
	 * ftp服务器操作工具
	 */
	@Override
	public IFtpClient ftpClient() {
		return (IFtpClient) LoadPhoenixPlugins.getPlugin("FtpClient");
	}
	/**
	 * 接口测试
	 */
	@Override
	public IInterfaceAPI interfaceAPI() {
		return interfaceAPI;
	}
	/**
	 * 通用方法
	 */
	@Override
	public ICommonAPI commonAPI() {
		return commonAPI;
	}
}
