package com.qingdao.marathon.user.model;
/**
 * 赛事信息
 * @author wqy
 *
 */
public class MatchInfo {

	private String matchId;
	private String matchName;
	private String startTime;//开始时间
	private String regDeadline;//报名截止时间
	private String fee;//报名费
	private String state;//状态
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
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
