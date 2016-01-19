package org.phoenix.svn.action;

import java.util.HashMap;
import java.util.List;

import org.phoenix.svn.client.SvnLogModel;
import org.phoenix.svn.client.SvnOper;

/**
 * svn客户端实现类
 * @author mengfeiyang
 *
 */
public class SvnClient implements ISvnClient{
	private SvnOper svnOper;
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#configSvnClient(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ISvnClient configSvnClient(String svnUrl, String username,
			String password, String definePath) {
		svnOper = new SvnOper(svnUrl,username,password,definePath);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#checkOut()
	 */
	@Override
	public String checkOut() {
		return svnOper.checkOut();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#disPayFileAttributes()
	 */
	@Override
	public HashMap<String, String> disPayFileAttributes() {
		return svnOper.disPayFileAttributes();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#displaySvnLog()
	 */
	@Override
	public List<SvnLogModel> displaySvnLog() {
		return svnOper.displaySvnLog();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#doCommit()
	 */
	@Override
	public String doCommit() {
		return svnOper.doCommit();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#doDiff()
	 */
	@Override
	public String doDiff() {
		return svnOper.doDiff();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#doImport()
	 */
	@Override
	public String doImport() {
		return svnOper.doImport();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#doUpdate()
	 */
	@Override
	public String doUpdate() {
		return svnOper.doUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.phoenix.svn.action.ISvnClient#disPlayRespositoryTree()
	 */
	@Override
	public void disPlayRespositoryTree() {
		svnOper.disPlayRespositoryTree();
	}
}
