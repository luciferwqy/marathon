package com.qingdao.marathon.system.mapper;

import java.util.Map;

import com.qingdao.marathon.base.BaseMapper;

/**
 * 
 * @author wqy
 * @datetime 2016年7月27日
 * @func
 */
public interface RoleMapper<T> extends BaseMapper<T> {

	/**
	 * 根据roleid删除角色和功能绑定关系
	 * @param roleid
	 */
	void delRoleBindFunc(String roleid);
	
	/**
	 * 根据roleid删除员工和角色绑定关系
	 * @param roleid
	 */
	void delRoleBindOpt(String roleid);

	/**
	 * 更新权限
	 * @param parms
	 */
	void addPriv(Map<String, Object> parms);
}
