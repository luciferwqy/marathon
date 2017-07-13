package com.qingdao.marathon.user.model;
/**
 * 个人信息
 * @author 王琪云
 *
 */
public class PersonalInfo {

	private String account;//账号
	private String pwd;//密码
	private String name;//姓名
	private String nationality;//国籍
	private Integer IDType;//证件类型 0:身份证
	private String IDNumber;//证件号码
	private String sex;//性别
	private String birthDay;//生日
	private String mobilPhone;//手机
	private String email;//邮箱
	private String residence;//居住地
	private String address;//通信地址
	private String bloodType;//血型
	private String occupation;//职业
	private String education;//教育程度
	private String university;//大学
	private String urgentPerson;//紧急联系人
	private String urgentPhone;//紧急联系人电话
	private String backupPhone;//备用电话
	private String backupMail;//备用邮箱
	private MarathonRace race;//参加过的马拉松
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Integer getIDType() {
		return IDType;
	}
	public void setIDType(Integer iDType) {
		IDType = iDType;
	}
	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getMobilPhone() {
		return mobilPhone;
	}
	public void setMobilPhone(String mobilPhone) {
		this.mobilPhone = mobilPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getUrgentPerson() {
		return urgentPerson;
	}
	public void setUrgentPerson(String urgentPerson) {
		this.urgentPerson = urgentPerson;
	}
	public String getUrgentPhone() {
		return urgentPhone;
	}
	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}
	public String getBackupPhone() {
		return backupPhone;
	}
	public void setBackupPhone(String backupPhone) {
		this.backupPhone = backupPhone;
	}
	public String getBackupMail() {
		return backupMail;
	}
	public void setBackupMail(String backupMail) {
		this.backupMail = backupMail;
	}
	public MarathonRace getRace() {
		return race;
	}
	public void setRace(MarathonRace race) {
		this.race = race;
	}
}
