package com.qingdao.marathon.system.mapper;

import java.util.Map;

import com.qingdao.marathon.base.BaseMapper;
import com.qingdao.marathon.system.model.Operator;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func
 */
public interface OperatorMapper<T> extends BaseMapper<T> {

	/**
	 * 根據登陸信息查詢操作人員
	 * 
	 * @param params
	 */
	Operator selectByLoginInfo(Map<String, String> params);
	
	/**
	 * 根据badge查询
	 * @param badge
	 * @return
	 */
	Operator selectBadge(String badge);
	
	/**
	 * 根据opt更新role和opt的绑定关系
	 * @param opt
	 */
	void updateRoleOpt(Operator opt);
	
	/**
	 * 根据opt新增role和opt的绑定关系
	 * @param opt
	 */
	void addRoleOpt(Operator opt);
	
	/**
	 * 删除员工和角色绑定关系
	 * @param optid
	 */
	void delOptBindRole(String optid);
	
}
