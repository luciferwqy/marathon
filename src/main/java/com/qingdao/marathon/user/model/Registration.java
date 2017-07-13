package com.qingdao.marathon.user.model;
/**
 * 报名信息
 * @author wqy
 *
 */
public class Registration {

	private String matchId;//赛事ID
	private String account;//账号
	private String nationality;//国籍
	private String matchName;//赛事名称
	private String auditState;//是否审核通过
	private String payState;//是否支付
	private String groupId;//组别ID
	private String groupName;//组别ID
	private String fee;
	private String fee2;
	private String competitionNo;//参赛号
	private String raceOrderId;//报名费支付订单号
	private String achievement;//成绩
	private String ranking;
	private String createtime;
	private String updatetime;
	private String remark;
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getFee2() {
		return fee2;
	}
	public void setFee2(String fee2) {
		this.fee2 = fee2;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
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
	public String getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCompetitionNo() {
		return competitionNo;
	}
	public void setCompetitionNo(String competitionNo) {
		this.competitionNo = competitionNo;
	}
	public String getRaceOrderId() {
		return raceOrderId;
	}
	public void setRaceOrderId(String raceOrderId) {
		this.raceOrderId = raceOrderId;
	}
	public String getAchievement() {
		return achievement;
	}
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
