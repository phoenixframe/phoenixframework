package org.phoenix.node.action;

import org.phoenix.model.BatchLogBean;
import org.phoenix.node.dto.AjaxObj;
/**
 * 任务执行入口接口
 * @author mengfeiyang
 *
 */
public interface RunAction {
	AjaxObj action(BatchLogBean batchLogBean);
}
