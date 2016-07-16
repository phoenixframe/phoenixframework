package org.phoenix.node.action;

import java.util.List;

import org.phoenix.dao.CaseDao;
import org.phoenix.dao.IModelDao;
import org.phoenix.dao.ScenarioLogDao;
import org.phoenix.model.BatchLogBean;
import org.phoenix.model.CaseBean;
import org.phoenix.model.ScenarioLogBean;
import org.phoenix.node.dto.AjaxObj;
import org.phoenix.node.model.TaskModel;
/**
 * 测试场景任务执行类，场景中可组合webUI，接口，mobile的测试任务。<br>
 * 通过循环调用{@link CaseAction}类完成任务的执行
 * @author mengfeiyang
 *
 */
public class ScenarioAction implements RunAction{
	private TaskModel taskModel;
	private ScenarioLogBean scenarioLogBean;

	public ScenarioAction(TaskModel taskModel, ScenarioLogBean scenarioLogBean) {
		super();
		this.taskModel = taskModel;
		this.scenarioLogBean = scenarioLogBean;
	}

	@Override
	public AjaxObj action(BatchLogBean batchLogBean) {
		IModelDao<CaseBean> caseDao = new CaseDao();
		ScenarioLogDao scenarioLogDao = new ScenarioLogDao();
		List<CaseBean> caseList = caseDao.getModelList(Integer.parseInt(taskModel.getTaskData()));
		for(CaseBean caseBean : caseList){
			new CaseAction(caseBean,taskModel,scenarioLogBean,true).action(batchLogBean);
		}
		scenarioLogDao.update(scenarioLogBean);
		return new AjaxObj(1,scenarioLogBean.getScenarioName()+" 场景下所有的用例执行完成！");
	}
}
