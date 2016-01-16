package org.phoenix.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.phoenix.enums.MsgStatusType;

@Entity
@Table(name="t_msgpool")
public class MsgBean {
	private int id;
	private int taskModelId;
	private String msgContent;
	private MsgStatusType msgStatusType;
	private boolean isDeleteMsg;
	private String remark;
	private Date createData;
	private int userId;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTaskModelId() {
		return taskModelId;
	}
	public void setTaskModelId(int taskModelId) {
		this.taskModelId = taskModelId;
	}
	@Column(length=65500)
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	@Enumerated(EnumType.STRING)
	public MsgStatusType getMsgStatusType() {
		return msgStatusType;
	}
	public void setMsgStatusType(MsgStatusType msgStatusType) {
		this.msgStatusType = msgStatusType;
	}
	
	public boolean isDeleteMsg() {
		return isDeleteMsg;
	}
	public void setDeleteMsg(boolean isDeleteMsg) {
		this.isDeleteMsg = isDeleteMsg;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateData() {
		return createData;
	}
	public void setCreateData(Date createData) {
		this.createData = createData;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
