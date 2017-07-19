package com.qingdao.marathon.system.model;

/**
 * 功能实体
 * 
 * @author wqy
 * @datetime 2016年12月20日
 * @func
 */
public class ParticipantEntity {
	private String matchId;
	private String matchName;
	private String groupId;
	private String groupName;
	private String name;
	private String competitionNo;
	private String mobilPhone;
	private String IDNumber;
	private String email;
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompetitionNo() {
		return competitionNo;
	}
	public void setCompetitionNo(String competitionNo) {
		this.competitionNo = competitionNo;
	}
	public String getMobilPhone() {
		return mobilPhone;
	}
	public void setMobilPhone(String mobilPhone) {
		this.mobilPhone = mobilPhone;
	}
	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
