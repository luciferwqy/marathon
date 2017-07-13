package com.qingdao.marathon.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingdao.marathon.base.Pagination;
import com.qingdao.marathon.system.mapper.FuncMapper;
import com.qingdao.marathon.system.mapper.RoleMapper;
import com.qingdao.marathon.system.model.FuncEntity;
import com.qingdao.marathon.system.model.RoleEntity;
import com.qingdao.marathon.system.service.RoleMngService;

@Service
@SuppressWarnings("rawtypes")
public class RoleMngServiceImpl implements RoleMngService{

	@Resource
	RoleMapper<RoleEntity> roleMapper;
	
	@Resource
	FuncMapper funcMapper;
	
	@Override
	public Page<RoleEntity> queryByList(Pagination pagination,Map<String,Object> parms,boolean flag) {
		PageHelper.startPage(pagination.getCurrentPage(), pagination.getNumPerPage(), flag);
		return roleMapper.queryByList(parms);
	}

	@Override
	public void delRole(String roleid) {
		
		roleMapper.delete(roleid);
		roleMapper.delRoleBindFunc(roleid);
		roleMapper.delRoleBindOpt(roleid);
	}

	@Override
	public void updateRole(RoleEntity roleEntity) {
		roleMapper.update(roleEntity);
	}

	@Override
	public void addRole(RoleEntity roleEntity) {
		roleMapper.add(roleEntity);
	}

	@Override
	public void updatePriv(Map<String, String> privObj,String roleid,String optName) {
		//先删除所有权限
		roleMapper.delRoleBindFunc(roleid);
		//用于更新权限的Map
		Map<String,Object> parms = null;
		//用来插入父节点
		Map<String,Object> parentParms = null;
		for(Map.Entry<String, String> entry : privObj.entrySet()){
			parms = new HashMap<String,Object>();
			parentParms = new HashMap<String,Object>();
			parms.put("funcid", entry.getKey());
			//获取父funcid
			FuncEntity funcEntity = funcMapper.queryById(entry.getKey());
			
			parms.put("roleid", roleid);
			parms.put("privilege", entry.getValue());
			parms.put("opt", optName);
			roleMapper.addPriv(parms);
			
			parentParms.put("funcid", funcEntity.getParentId());
			parentParms.put("roleid", roleid);
			parentParms.put("privilege", entry.getValue());
			parentParms.put("opt", optName);
			roleMapper.addPriv(parentParms);
		}
		
	}
}
