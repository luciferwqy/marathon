package com.qingdao.marathon.express.model;

import java.io.Serializable;
import java.util.List;

public class RoteModel implements Serializable{

	private String ebusinessid;
	private String shippercode;//快递公司编码
	private String success;
	private String logisticcode;//快递单号
	private String state;
	private List<TracesModel> traces;
	public String getEbusinessid() {
		return ebusinessid;
	}
	public void setEbusinessid(String ebusinessid) {
		this.ebusinessid = ebusinessid;
	}
	public String getShippercode() {
		return shippercode;
	}
	public void setShippercode(String shippercode) {
		this.shippercode = shippercode;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getLogisticcode() {
		return logisticcode;
	}
	public void setLogisticcode(String logisticcode) {
		this.logisticcode = logisticcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<TracesModel> getTraces() {
		return traces;
	}
	public void setTraces(List<TracesModel> traces) {
		this.traces = traces;
	}
	
}
