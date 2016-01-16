package org.phoenix.node.action;

import org.phoenix.model.BatchLogBean;
import org.phoenix.node.dto.AjaxObj;

public interface RunAction {
	AjaxObj action(BatchLogBean batchLogBean);
}
