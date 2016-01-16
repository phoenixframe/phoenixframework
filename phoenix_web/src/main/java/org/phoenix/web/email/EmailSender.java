package org.phoenix.web.email;

import java.util.Date;
import java.util.List;

import org.phoenix.enums.MsgStatusType;
import org.phoenix.model.MsgBean;
import org.phoenix.utils.GetNow;
import org.phoenix.web.model.TaskModel;
import org.phoenix.web.model.User;
import org.phoenix.web.service.IMsgService;
import org.phoenix.web.service.ITaskService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 用于发送Email消息
 * @author mengfeiyang
 *
 */
public class EmailSender {

	public void send(){
		String[] toEmails = null;
		IMsgService msgService = (IMsgService)SpringBeanFactory.getInstance().getBeanById("msgService");
		ITaskService taskService = (ITaskService)SpringBeanFactory.getInstance().getBeanById("taskService");
		List<MsgBean> msgBeanList = msgService.getMsgBeanListForEmail();
		for(MsgBean msgBean : msgBeanList){
			try{
				TaskModel taskModel = taskService.getTaskModel(msgBean.getTaskModelId());
				User u = taskModel.getUser();
				SimpleMailMessage mail = new SimpleMailMessage();
				if(u.getEmail().contains(";"))toEmails = u.getEmail().split(";");
				else toEmails = new String[]{u.getEmail()};	
				JavaMailSenderImpl sender = (JavaMailSenderImpl)SpringBeanFactory.getInstance().getBeanById("mailSender");
				
				mail.setTo(toEmails);
				mail.setSubject("PhoenixFramework自动化测试平台测试结果报告 - "+GetNow.getCurrentTime());
				mail.setSentDate(new Date());
				mail.setFrom(sender.getJavaMailProperties().getProperty("mail.setFrom"));
				mail.setText(msgBean.getMsgContent()+"\r\n\r\n此邮件由系统自动发送，请勿回复！");
				sender.send(mail);
				msgBean.setMsgStatusType(MsgStatusType.SUCCESS);
				msgBean.setRemark("Sent Complete");		
				if(msgBean.isDeleteMsg())msgService.deleteMsg(msgBean.getId());
				else msgService.update(msgBean);
			}catch(Exception e){
				msgBean.setMsgStatusType(MsgStatusType.FAIL);
				msgBean.setRemark("Send Fail:"+e.getClass().getSimpleName()+","+e.getCause());
				msgService.update(msgBean);
			}
		}
	}
}
