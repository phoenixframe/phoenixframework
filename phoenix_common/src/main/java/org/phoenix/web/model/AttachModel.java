package org.phoenix.web.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_attach")
public class AttachModel {
	private int id;
	private String attachName;
	private String attachPath;
	private String attachType;
	private Date createDate;
	private User user;
	public AttachModel() {
	}
	
	public AttachModel(String attachName, String attachPath, String attachType,
			Date createDate, User user) {
		super();
		this.attachName = attachName;
		this.attachPath = attachPath;
		this.attachType = attachType;
		this.createDate = createDate;
		this.user = user;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(unique=true,nullable=false)
	public String getAttachName() {
		return attachName;
	}
	
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public String getAttachType() {
		return attachType;
	}
	public void setAttachType(String attachType) {
		this.attachType = attachType;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
