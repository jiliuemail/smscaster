package com.skyline.sms.caster.pojo;
// Generated 2015-9-30 11:50:52 by Hibernate Tools 4.0.0.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TUser generated by hbm2java
 */
public class TUser implements java.io.Serializable {

	private Integer id;
	private String userName;
	private String nickName;
	private String number;
	private String userType;
	private int receive;
	private Date createDate;
	
	private Set<TGroup> TGroups = new HashSet<TGroup>(0);

	public TUser() {
	}

	public TUser(String userName, String number, int receive) {
		this.userName = userName;
		this.number = number;
		this.receive = receive;
	}

	public TUser(String userName, String nickName, String number, String userType, int receive, Set<TGroup> TGroups) {
		this.userName = userName;
		this.nickName = nickName;
		this.number = number;
		this.userType = userType;
		this.receive = receive;
		this.TGroups = TGroups;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getReceive() {
		return this.receive;
	}

	public void setReceive(int receive) {
		this.receive = receive;
	}

	public Set<TGroup> getTGroups() {
		return this.TGroups;
	}

	public void setTGroups(Set<TGroup> TGroups) {
		this.TGroups = TGroups;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}
