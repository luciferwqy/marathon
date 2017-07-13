package com.qingdao.marathon.system.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.model.RoleEntity;

public interface RoleMngService {

	/**
	 * 查询角色
	 * @param parms
	 * @return
	 */
	Page<RoleEntity> queryByList(Pagination pagination,Map<String,Object> parms,boolean flag);
	
	/**
	 * 根据roleid删除
	 * @param roleid
	 */
	void delRole(String roleid);

	/**
	 * 根据role更新
	 * @param roleEntity
	 */
	void updateRole(RoleEntity roleEntity);

	/**
	 * 根据role新增
	 * @param roleEntity
	 */
	void addRole(RoleEntity roleEntity);

	/**
	 * 更新权限
	 * @param parms
	 */
	void updatePriv(Map<String, String> parms,String roleid,String optName);
}
