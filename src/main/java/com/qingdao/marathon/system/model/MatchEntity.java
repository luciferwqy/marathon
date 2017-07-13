package com.qingdao.marathon.system.model;

/**
 * 功能实体
 * 
 * @author wqy
 * @datetime 2016年12月20日
 * @func
 */
public class MatchEntity {
	private String matchId;
	private String matchName;
	private String startTime;
	private String regDeadline;
	private String state;
	private String createTime;
	private String updateTime;
	private String opt;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getRegDeadline() {
		return regDeadline;
	}
	public void setRegDeadline(String regDeadline) {
		this.regDeadline = regDeadline;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
