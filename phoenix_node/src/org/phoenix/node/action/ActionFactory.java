package org.phoenix.node.action;

import java.util.Date;
import java.util.concurrent.Callable;

import org.phoenix.dao.BatchLogDao;
import org.phoenix.dao.CaseDao;
import org.phoenix.dao.ScenarioDao;
import org.phoenix.dao.ScenarioLogDao;
import org.phoenix.enums.TaskStatusType;
import org.phoenix.model.BatchLogBean;
import org.phoenix.model.CaseBean;
import org.phoenix.model.ScenarioBean;
import org.phoenix.model.ScenarioLogBean;
import org.phoenix.node.dao.SlaveDao;
import org.phoenix.node.dao.TaskDao;
import org.phoenix.node.dto.AjaxObj;
import org.phoenix.node.dto.TaskDataDTO;
import org.phoenix.node.dto.TaskType;
import org.phoenix.node.model.SlaveModel;
import org.phoenix.node.model.TaskModel;
import org.phoenix.node.util.GetNow;

/**
 * action工厂
 * @author mengfeiyang
 *
 */
public class ActionFactory implements Callable<AjaxObj>{
	private TaskType taskType;
	private TaskDataDTO taskDataDTO;
	private TaskDao taskDao = new TaskDao();
	private BatchLogBean batchLogBean = new BatchLogBean();
	private TaskModel taskModel;
	private SlaveModel slaveModel;
	private BatchLogDao batchLogDao = new BatchLogDao();
	private SlaveDao slaveDao = new SlaveDao();
	
	public TaskDataDTO getTaskDataDTO() {
		return taskDataDTO;
	}

	public void setTaskDataDTO(TaskDataDTO taskDataDTO) {
		this.taskDataDTO = taskDataDTO;
		this.taskType = taskDataDTO.getTaskType();
	}

	public RunAction getRunAction(){
		taskModel = taskDao.getTaskModel(taskDataDTO.getTaskId());
		taskModel.setStartTime(null);
		taskModel.setEndTime(null);
		taskModel.setStartTime(new Date());
		taskModel.setTaskStatusType(TaskStatusType.RUNNING);
		batchLogBean.setBatchId(GetNow.getCurrentTime("yyyyMMddhhmmss"));
		batchLogBean.setCreateDate(new Date());
		batchLogBean.setTaskType(taskType+"");
		batchLogBean.setUid(taskModel.getUserId());
		slaveModel = taskModel.getSlaveModel();
		slaveModel.setAttachPath(taskDataDTO.getContentPath());
		BatchLogBean batchBean = batchLogDao.add(batchLogBean);
		taskModel.setBatchLogId(batchBean.getId());
		taskDao.update(taskModel);
		slaveDao.update(slaveModel);
		int modelId = Integer.parseInt(taskModel.getTaskData());
		switch(taskType){
		case WEB_CASE:
		case MOBILE_CASE:
		case INTERFACE_CASE:
			CaseDao caseDao = new CaseDao();
			CaseBean caseBean = caseDao.load(modelId);
			return new CaseAction(caseBean,taskModel,null,false);
		case WEB_SCENARIO:
			ScenarioDao scenarioDao = new ScenarioDao();
			ScenarioLogDao scenarioLogDao = new ScenarioLogDao();
			ScenarioLogBean scenarioLogBean = new ScenarioLogBean();
			ScenarioBean sceanrioBean = scenarioDao.loadModel(modelId);
			scenarioLogBean.setBatchLogBean(batchLogBean);
			scenarioLogBean.setScenarioName(sceanrioBean.getScenarioName());
			scenarioLogBean.setUid(taskModel.getUserId());
			ScenarioLogBean s = scenarioLogDao.add(scenarioLogBean);
			return new ScenarioAction(taskModel,s);

		default: return null;
		}
	}

	@Override
	public AjaxObj call() throws Exception {
		
		AjaxObj ajaxObj = getRunAction().action(batchLogBean);
		taskModel.setEndTime(new Date());
		taskDao.update(taskModel);
		return ajaxObj;
	}
}
