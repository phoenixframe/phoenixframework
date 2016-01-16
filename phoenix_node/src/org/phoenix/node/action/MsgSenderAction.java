package org.phoenix.node.action;

import java.util.Date;
import java.util.LinkedList;

import org.phoenix.dao.MsgDao;
import org.phoenix.enums.MsgSendType;
import org.phoenix.enums.MsgStatusType;
import org.phoenix.model.CaseBean;
import org.phoenix.model.MsgBean;
import org.phoenix.model.UnitLogBean;
import org.phoenix.node.model.TaskModel;

/**
 * 为发送消息准备内容
 * @author mengfeiyang
 *
 */
public class MsgSenderAction extends MsgDao{
	private String newLine = "\r\n";
	private String blanks = "     ";
	
	public int sendEmail(TaskModel taskModel,CaseBean caseBean,LinkedList<UnitLogBean> unitLogs){
		StringBuilder commonLog = new StringBuilder();
		StringBuilder stepFailLog = new StringBuilder();
		StringBuilder checkPointFailLog = new StringBuilder();
		stepFailLog.append("以下的 STEP 执行失败："+newLine);
		checkPointFailLog.append("以下的 CHECKPOINT 执行失败："+newLine);
		int checkPointCount=0;
		int stepCount=0;
		commonLog.append("任务类型："+taskModel.getTaskType()+newLine);
		commonLog.append("任务名称："+taskModel.getTaskName()+newLine);
		commonLog.append("任务Id："+taskModel.getId()+newLine);
		commonLog.append("执行机信息："+taskModel.getSlaveModel().getSlaveIP()+newLine);
		commonLog.append("关联的实体："+taskModel.getBeanName()+newLine);
		commonLog.append("本次报告问题的用例："+caseBean.getCaseName()+newLine);
		commonLog.append("用例功能说明："+caseBean.getRemark()+newLine);
		commonLog.append("消息发送类型："+caseBean.getMsgSendType()+newLine);
		commonLog.append("详细日志内容如下："+newLine);
		for(UnitLogBean u : unitLogs){
			if(u.getStepType().equals("CHECKPOINT") && u.getStatus().equals("FAIL")){
				checkPointCount++;
				checkPointFailLog.append(checkPointCount+". 检查点名称:"+u.getStepName()+newLine);
				checkPointFailLog.append(blanks+"状态："+u.getStatus()+newLine);
				checkPointFailLog.append(blanks+"截图地址："+u.getScreenShot()+newLine);
				checkPointFailLog.append(blanks+"内容："+u.getContent()+newLine+newLine);
			}
			if(u.getStepType().equals("STEP") && u.getStatus().equals("FAIL")){
				stepCount++;
				stepFailLog.append(stepCount+". 步骤名称："+u.getStepName()+newLine);
				stepFailLog.append(blanks+"状态："+u.getStatus()+newLine);
				stepFailLog.append(blanks+"截图地址："+u.getScreenShot()+newLine);
				stepFailLog.append(blanks+"内容："+u.getContent()+newLine+newLine);
			}
		}
		
		MsgBean msgBean = new MsgBean();
		msgBean.setCreateData(new Date());
		msgBean.setDeleteMsg(caseBean.isDeleteMsg());
		msgBean.setMsgStatusType(MsgStatusType.WAITING);
		msgBean.setTaskModelId(taskModel.getId());
		msgBean.setUserId(caseBean.getUserId());
		
		MsgSendType msgSendType = caseBean.getMsgSendType();
		switch(msgSendType){
			case NOT_SEND:break;
			case CHECKPOINT_FAIL:
				if(!checkPointFailLog.toString().trim().equals("以下的 CHECKPOINT 执行失败：")){
					msgBean.setMsgContent(commonLog.toString()+checkPointFailLog.toString());
					add(msgBean);
				}
				break;
			case STEP_FAIL:
				if(!stepFailLog.toString().trim().equals("以下的 STEP 执行失败：")){
					msgBean.setMsgContent(commonLog.toString()+stepFailLog.toString());
					add(msgBean);
				}
				break;
			case CHECKPOINT_STEP_FAIL:
				if(!checkPointFailLog.toString().trim().equals("以下的 CHECKPOINT 执行失败：")||!stepFailLog.toString().trim().equals("以下的 STEP 执行失败：")){
					msgBean.setMsgContent(commonLog.toString()+stepFailLog.toString()+checkPointFailLog.toString());
					add(msgBean);
				}
				break;
			case CASE_COMPLETE:
				msgBean.setMsgContent(commonLog.toString()+stepFailLog.toString()+checkPointFailLog.toString());
				add(msgBean);
				break;
			default:;
		}
		
		return 1;
	}

}
