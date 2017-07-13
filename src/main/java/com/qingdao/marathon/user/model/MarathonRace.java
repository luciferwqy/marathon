package com.qingdao.marathon.user.model;

public class MarathonRace {

	private String account;
	private String marathonName;//赛事名称
	private String achievement;//成绩
	private String certificatePath;//证书地址
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMarathonName() {
		return marathonName;
	}
	public void setMarathonName(String marathonName) {
		this.marathonName = marathonName;
	}
	public String getAchievement() {
		return achievement;
	}
	public void setAchievement(String achievement) {
		this.achievement = achievement;
	}
	public String getCertificatePath() {
		return certificatePath;
	}
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	
}
