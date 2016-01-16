package org.phoenix.web.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="t_user")
@BatchSize(size=30)
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;
	private String nickname;
	private int role;
	private String roleName;
	private String email;
	private Date createDate;
	private Set<TaskModel> taskModels;
	private Set<AttachModel> attachModels;
	
	public User() {
	}
	
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(int id) {
		super();
		this.id = id;
	}

	public User(int id, String nickname) {
		super();
		this.id = id;
		this.nickname = nickname;
	}
	@NotBlank(message="{user.email.notBlack}")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
    
	@OneToMany(mappedBy="user",targetEntity=TaskModel.class,cascade={CascadeType.ALL},orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SUBSELECT)
	public Set<TaskModel> getTaskModels() {
		return taskModels;
	}

	public void setTaskModels(Set<TaskModel> taskModels) {
		this.taskModels = taskModels;
	}
	@OneToMany(mappedBy="user",targetEntity=AttachModel.class)
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SUBSELECT)
	public Set<AttachModel> getAttachModels() {
		return attachModels;
	}

	public void setAttachModels(Set<AttachModel> attachModels) {
		this.attachModels = attachModels;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@NotEmpty(message="{user.username.error}")
	@Column(unique=true,nullable=false)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@NotEmpty(message="{user.password.error}")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(unique=true,nullable=false)
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}	
	
}
