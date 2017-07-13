package com.qingdao.marathon.express.model;

import java.io.Serializable;

public class TracesModel implements Serializable{

	private String accepttime;//时间
	private String acceptstation;//时间+地址+派送人姓名
	private String remark;
	public String getAccepttime() {
		return accepttime;
	}
	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}
	public String getAcceptstation() {
		return acceptstation;
	}
	public void setAcceptstation(String acceptstation) {
		this.acceptstation = acceptstation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
