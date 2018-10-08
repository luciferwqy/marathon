package com.qingdao.marathon.model;

/**
 * ajax统一返回类型
 * @author 王琪云
 *
 */
public class ResultModel {

	private String msg;//消息
	private boolean success;//是否成功
	private Object obj;//实体对象
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		return "ResultModel [msg=" + msg + ", success=" + success + ", obj=" + obj + "]";
	}
}
