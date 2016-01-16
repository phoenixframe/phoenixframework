package org.phoenix.web.quartz;

import org.phoenix.web.email.EmailSender;

/**
 * 初始化消息池，系统重新启动时会将消息池中状态为WAITING，FAIL类型的消息重新<br>
 * 添加到定时器队列重新发送
 * @author mengfeiyang
 *
 */
public class InitMsg {
	private EmailSender emailSender = new EmailSender();
	public void init(){
		new Thread(new Runnable(){
			@Override
			public void run() {
				emailSender.send();
			}
		}).start();
	}
}
