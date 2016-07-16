package org.phoenix.api.impl;

import org.phoenix.mobile.ios.action.IIOSAppAPI;
import org.phoenix.model.CaseLogBean;

/**
 * IOS操作实现类
 * @author mengfeiyang
 *
 */
public class IOSAppAPI extends MobileCommon implements IIOSAppAPI{
	private CaseLogBean caseLogBean;
	public IOSAppAPI(CaseLogBean caseLogBean) {
		this.caseLogBean = caseLogBean;
	}
}
