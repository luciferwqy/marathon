package com.qingdao.marathon.system.model;

/**
 * 功能实体
 * 
 * @author wqy
 * @datetime 2016年12月20日
 * @func
 */
public class MatchGroupEntity {
	private String matchId;
	private String matchName;
	private String groupId;
	private String groupName;
	private String fee;
	private String fee2;
	private String hasDraw;
	private String createTime;
	private String updateTime;
	private String opt;
	
	public String getFee2() {
		return fee2;
	}
	public void setFee2(String fee2) {
		this.fee2 = fee2;
	}
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
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getHasDraw() {
		return hasDraw;
	}
	public void setHasDraw(String hasDraw) {
		this.hasDraw = hasDraw;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	
}
