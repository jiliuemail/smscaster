package com.skyline.sms.caster.pojo;
// Generated 2015-9-30 11:50:52 by Hibernate Tools 4.0.0.Final

import java.util.HashSet;
import java.util.Set;

/**
 * TGroup generated by hbm2java
 */
public class TGroup implements java.io.Serializable {

	private Integer id;
	private String groupName;
	private String groupType;
	private int receive;
	private Set<TUser> TUsers = new HashSet<TUser>(0);

	public TGroup() {
	}

	public TGroup(String groupName, int receive) {
		this.groupName = groupName;
		this.receive = receive;
	}

	public TGroup(String groupName, String groupType, int receive, Set<TUser> TUsers) {
		this.groupName = groupName;
		this.groupType = groupType;
		this.receive = receive;
		this.TUsers = TUsers;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public int getReceive() {
		return this.receive;
	}

	public void setReceive(int receive) {
		this.receive = receive;
	}

	public Set<TUser> getTUsers() {
		return this.TUsers;
	}

	public void setTUsers(Set<TUser> TUsers) {
		this.TUsers = TUsers;
	}

}
