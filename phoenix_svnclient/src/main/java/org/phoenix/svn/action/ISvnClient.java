package org.phoenix.svn.action;

import java.util.HashMap;
import java.util.List;

import org.phoenix.svn.client.SvnLogModel;

/**
 * phoenixframe的svn客户端模块
 * @author mengfeiyang
 *
 */
public interface ISvnClient {
	/**
	 * 重要参数配置配置
	 * @param svnUrl  svn资源路径
	 * @param username 登陆用户名
	 * @param password 登陆密码
	 * @param definePath 如果做提交，则此路径为要提交的本地路径，如果做下载，则此路径为要指定的本地路径
	 */
	ISvnClient configSvnClient(String svnUrl,String username,String password,String definePath);
	/**
	 * 检出资源
	 * @return
	 */
	String checkOut();
	/**
	 * 显示svn服务器上指定文件的所有属性
	 * @return
	 */
	HashMap<String,String> disPayFileAttributes();
	/**
	 * 显示svn日志
	 * @return
	 */
	List<SvnLogModel> displaySvnLog();
	/**
	 * 提交操作
	 * @return
	 */
	String doCommit();
	/**
	 * 检出不同
	 * @return
	 */
	String doDiff();
	/**
	 * 上传操作
	 * @return
	 */
	String doImport();
	/**
	 * 更新操作
	 * @return
	 */
	String doUpdate();
	
	/**
	 * 显示资源树
	 * @return
	 */
	void disPlayRespositoryTree();
}
